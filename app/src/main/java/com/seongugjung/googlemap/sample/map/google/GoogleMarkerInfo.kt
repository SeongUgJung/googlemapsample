package com.seongugjung.googlemap.sample.map.google

import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.Marker
import com.seongugjung.googlemap.sample.location.LatLong
import com.seongugjung.googlemap.sample.map.marker.MarkerInfo

class GoogleMarkerInfo(private val marker: Marker) : MarkerInfo {
    override fun updateLocation(latLong: LatLong) {
        marker.position = LatLng(latLong.latitude, latLong.longitude)
    }

    override fun remove() {
        marker.remove()
    }
}