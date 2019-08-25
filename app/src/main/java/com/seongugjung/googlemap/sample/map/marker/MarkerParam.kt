package com.seongugjung.googlemap.sample.map.marker

import androidx.annotation.DrawableRes
import com.seongugjung.googlemap.sample.location.LatLong

data class MarkerParam(
    @DrawableRes val icon: Int,
    val latLong: LatLong,
    val anchor: Pair<Float, Float> = 0.5f to 1f,
    val zIndex: Int = 1
)