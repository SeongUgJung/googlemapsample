package com.seongugjung.googlemap.sample.view.mapinteractors

import androidx.databinding.ObservableField
import com.seongugjung.googlemap.sample.base.extensions.asObservable
import com.seongugjung.googlemap.sample.map.Map
import com.seongugjung.googlemap.sample.map.MapInteractor
import com.seongugjung.googlemap.sample.map.marker.MarkerParam
import io.reactivex.Completable
import io.reactivex.Observable

class MarkerInteractor : MapInteractor {

    private val markers: ObservableField<List<MarkerParam>> = ObservableField(emptyList())

    override fun onInit(map: Map): Completable {
        return markers.asObservable()
            .switchMap { markers ->
                Observable.fromIterable(markers)
                    .flatMap { map.addMarker(it) }
            }
            .ignoreElements()
    }

    fun setMarkers(markers: List<MarkerParam>) {
        this.markers.set(markers)
    }

}