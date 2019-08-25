package com.seongugjung.googlemap.sample.map

import com.seongugjung.googlemap.sample.location.LatLong
import com.seongugjung.googlemap.sample.map.marker.MarkerInfo
import com.seongugjung.googlemap.sample.map.marker.MarkerParam
import io.reactivex.Observable


object DEFAULT : Map {
    override fun zoomToLatLong(animate: Boolean, latLongs: LatLong): Observable<Boolean> =
        Observable.empty()

    override fun zoomToLatLongs(animate: Boolean, latLongs: List<LatLong>): Observable<Boolean> =
        Observable.empty()

    override fun addMarker(markerParam: MarkerParam): Observable<MarkerInfo> =
        Observable.empty()
}

interface Map {
    fun zoomToLatLong(animate: Boolean, latLongs: LatLong): Observable<Boolean>
    fun zoomToLatLongs(animate: Boolean, latLongs: List<LatLong>): Observable<Boolean>
    fun addMarker(markerParam: MarkerParam): Observable<MarkerInfo>
}