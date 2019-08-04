package com.seongugjung.googlemap.sample.map.google

import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.CameraPosition
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.LatLngBounds
import com.seongugjung.googlemap.sample.location.LatLong
import com.seongugjung.googlemap.sample.map.Map
import io.reactivex.Observable
import io.reactivex.Single

class GoogleMapImpl(private val googleMap: GoogleMap) : Map {
    override fun zoomToLatLong(animate: Boolean, vararg latLongs: LatLong): Observable<Boolean> {

        return Observable.just(latLongs)
            .switchMapSingle {
                if (latLongs.size > 1) {
                    Observable.fromIterable(latLongs.asIterable())
                        .collect({ LatLngBounds.builder() }, { builder, latLong ->
                            builder.include(LatLng(latLong.latitude, latLong.longitude))
                        })
                        .map { CameraUpdateFactory.newLatLngBounds(it.build(), 0) }
                } else {

                    Single.just(
                        CameraUpdateFactory.newCameraPosition(
                            CameraPosition.fromLatLngZoom(
                                LatLng(
                                    latLongs[0].latitude,
                                    latLongs[0].longitude
                                ), 15f
                            )
                        )
                    )
                }
            }.doOnNext {
                if (animate) {
                    googleMap.animateCamera(it)
                } else {
                    googleMap.moveCamera(it)
                }
            }.map { true }

    }
}