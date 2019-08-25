package com.seongugjung.googlemap.sample.map.google

import androidx.core.graphics.drawable.toBitmap
import com.google.android.libraries.maps.CameraUpdate
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.CameraPosition
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.LatLngBounds
import com.google.android.libraries.maps.model.MarkerOptions
import com.seongugjung.googlemap.sample.R
import com.seongugjung.googlemap.sample.base.resources.ResourceProvider
import com.seongugjung.googlemap.sample.base.rx.SchedulerManager
import com.seongugjung.googlemap.sample.location.LatLong
import com.seongugjung.googlemap.sample.map.Map
import com.seongugjung.googlemap.sample.map.marker.MarkerInfo
import com.seongugjung.googlemap.sample.map.marker.MarkerParam
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Single

class GoogleMapImpl(
    private val googleMap: GoogleMap,
    private val resourceProvider: ResourceProvider,
    private val schedulerManager: SchedulerManager
) : Map {
    init {
        val padding = resourceProvider.getDimen(R.dimen.default_padding)
        googleMap.setPadding(padding, padding, padding, padding)
    }

    override fun zoomToLatLong(animate: Boolean, latLong: LatLong): Observable<Boolean> {

        return Single.create<CameraUpdate> {
            it.onSuccess(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                        LatLng(
                            latLong.latitude,
                            latLong.longitude
                        ), 15f
                    )
                )
            )
        }.toObservable()
            .doOnNext {
                if (animate) {
                    googleMap.animateCamera(it)
                } else {
                    googleMap.moveCamera(it)
                }
            }.map { true }
            .subscribeOn(schedulerManager.ui())

    }

    override fun zoomToLatLongs(animate: Boolean, latLongs: List<LatLong>): Observable<Boolean> {

        if (latLongs.isEmpty()) {
            return Observable.empty()
        }

        if (latLongs.size == 1) {
            return zoomToLatLong(animate, latLongs[0])
        }
        return Observable.just(latLongs)
            .observeOn(schedulerManager.ui())
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

    override fun addMarker(markerParam: MarkerParam): Observable<MarkerInfo> {
        return Observable.create { emitter: ObservableEmitter<MarkerInfo> ->
            val marker = googleMap.addMarker(
                MarkerOptions().icon(
                    BitmapDescriptorFactory.fromBitmap(
                        resourceProvider.getDrawable(markerParam.icon)?.toBitmap()
                    )
                ).position(LatLng(markerParam.latLong.latitude, markerParam.latLong.longitude))
                    .anchor(markerParam.anchor.first, markerParam.anchor.second)
            )

            if (marker == null) {
                emitter.onComplete()
            }

            val googleMarkerInfo = GoogleMarkerInfo(marker)
            emitter.onNext(googleMarkerInfo)

            emitter.setCancellable {
                googleMarkerInfo.remove()
            }
        }.subscribeOn(schedulerManager.ui())
            .unsubscribeOn(schedulerManager.ui())
    }
}