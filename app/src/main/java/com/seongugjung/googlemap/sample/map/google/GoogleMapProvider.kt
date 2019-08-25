package com.seongugjung.googlemap.sample.map.google

import android.view.View
import androidx.fragment.app.FragmentManager
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.SupportMapFragment
import com.seongugjung.googlemap.sample.base.resources.ResourceProvider
import com.seongugjung.googlemap.sample.base.rx.SchedulerManager
import com.seongugjung.googlemap.sample.map.DEFAULT
import com.seongugjung.googlemap.sample.map.Map
import com.seongugjung.googlemap.sample.map.MapParams
import com.seongugjung.googlemap.sample.map.MapProvider
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.subjects.BehaviorSubject

class GoogleMapProvider(
    private val fragmentManager: FragmentManager,
    private val mapParams: MapParams,
    private val resourceProvider: ResourceProvider,
    private val schedulerManager: SchedulerManager
) : MapProvider {
    private val mapCache: BehaviorSubject<Map> = BehaviorSubject.createDefault(
        DEFAULT
    )

    override fun initMap(): Observable<Map> {
        return Observable.create(this::addMapFragment)
            .switchMapSingle(this::waitingForLoading)
            .doOnNext(mapCache::onNext)
    }

    private fun addMapFragment(emitter: ObservableEmitter<SupportMapFragment>) {
        val mapFragment = SupportMapFragment()
        mapFragment.retainInstance = false
        fragmentManager.beginTransaction()
            .replace(mapParams.resId.takeIf { it != View.NO_ID } ?: android.R.id.content,
                mapFragment)
            .commit()

        emitter.setCancellable {
            fragmentManager.beginTransaction()
                .remove(mapFragment)
                .commitAllowingStateLoss()
            mapCache.onNext(DEFAULT)
        }

        emitter.onNext(mapFragment)

    }

    private fun waitingForLoading(mapFragment: SupportMapFragment): Single<Map> {
        return Single.create { emitter: SingleEmitter<Map> ->
            mapFragment.getMapAsync { map: GoogleMap? ->
                map?.let { actualMap ->
                    actualMap.setOnMapLoadedCallback {
                        emitter.onSuccess(
                            GoogleMapImpl(
                                actualMap,
                                resourceProvider,
                                schedulerManager
                            )
                        )
                    }
                } ?: run { emitter.tryOnError(IllegalStateException("No Map")) }
            }
        }
    }

    override fun getMap(): Observable<Map> = mapCache.hide()
}