package com.seongugjung.googlemap.sample.map

import io.reactivex.Observable

interface MapProvider {
    fun initMap(): Observable<Map>
    fun getMap(): Observable<Map>
}