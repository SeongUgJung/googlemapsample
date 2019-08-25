package com.seongugjung.googlemap.sample.view

import androidx.databinding.ObservableBoolean
import com.seongugjung.googlemap.sample.R
import com.seongugjung.googlemap.sample.base.RxLifecycleDelegate
import com.seongugjung.googlemap.sample.base.extensions.asObservable
import com.seongugjung.googlemap.sample.base.onInit
import com.seongugjung.googlemap.sample.location.LatLong
import com.seongugjung.googlemap.sample.map.marker.MarkerParam
import com.seongugjung.googlemap.sample.view.mapinteractors.CenterInteractor
import com.seongugjung.googlemap.sample.view.mapinteractors.MarkerInteractor
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function3
import io.reactivex.internal.functions.Functions
import timber.log.Timber

class MapViewModel(
    rxLifecycleDelegate: RxLifecycleDelegate,
    private val centerInteractor: CenterInteractor,
    private val markerInteractor: MarkerInteractor
) :
    RxLifecycleDelegate by rxLifecycleDelegate {

    val google = ObservableBoolean(false)
    val facebook = ObservableBoolean(false)
    val changiAirport = ObservableBoolean(false)

    init {
        +onInit {
            Observable.combineLatest(
                google.asObservable(),
                facebook.asObservable(),
                changiAirport.asObservable(),
                Function3<Boolean, Boolean, Boolean, List<LatLong>> { google, fb, changi ->
                    mutableListOf<LatLong>().apply {
                        if (google) {
                            add(CenterInteractor.GGL_SINGAPORE)
                        }
                        if (fb) {
                            add(CenterInteractor.FB_SINGAPORE)
                        }

                        if (changi) {
                            add(CenterInteractor.CHANGI_SINGAPORE)
                        }
                    }
                })
                .doOnNext { list ->
                    centerInteractor.setLatLongs(list)
                    list.map { MarkerParam(R.drawable.ic_map_marker, it) }
                        .let { markerInteractor.setMarkers(it) }
                }
                .subscribe(Functions.emptyConsumer(), Consumer { Timber.e(it) })

        }
    }


    fun moveCenter() {
        centerInteractor.setCenter()
    }
}