package com.seongugjung.googlemap.sample.map.marker

import com.seongugjung.googlemap.sample.location.LatLong

interface MarkerInfo {
    fun updateLocation(latLong: LatLong)
    fun remove()
}