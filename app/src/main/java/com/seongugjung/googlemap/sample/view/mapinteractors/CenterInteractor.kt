package com.seongugjung.googlemap.sample.view.mapinteractors

import androidx.databinding.ObservableField
import com.seongugjung.googlemap.sample.base.extensions.asObservable
import com.seongugjung.googlemap.sample.location.LatLong
import com.seongugjung.googlemap.sample.map.Map
import com.seongugjung.googlemap.sample.map.MapInteractor
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject

class CenterInteractor : MapInteractor {

    private val centerLatLong = BehaviorSubject.createDefault(true)
    private val latLongs = ObservableField<List<LatLong>>(emptyList())

    fun setCenter() {
        centerLatLong.onNext(true)
    }

    override fun onVisible(map: Map): Completable =
        centerLatLong.switchMap {
            latLongs.asObservable()
                .switchMap { latLongs ->
                    map.zoomToLatLongs(true, latLongs)
                }
        }.ignoreElements()

    fun setLatLongs(latLongs: List<LatLong>) {
        this.latLongs.set(latLongs)
    }

    companion object {

        @JvmStatic
        val FB_SINGAPORE = LatLong(1.277236, 103.8524089)
        val GGL_SINGAPORE = LatLong(1.2764109, 103.7995215)
        val CHANGI_SINGAPORE = LatLong(1.3644256, 103.9893421)
    }
}