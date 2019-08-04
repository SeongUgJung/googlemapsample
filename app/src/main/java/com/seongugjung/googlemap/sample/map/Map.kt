package com.seongugjung.googlemap.sample.map

import com.seongugjung.googlemap.sample.location.LatLong
import io.reactivex.Observable


object DEFAULT : Map {
    override fun zoomToLatLong(animate: Boolean, vararg latLongs: LatLong): Observable<Boolean> = Observable.empty()
}

interface Map {
    fun zoomToLatLong(animate: Boolean, vararg latLongs: LatLong) : Observable<Boolean>
}