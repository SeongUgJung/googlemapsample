package com.seongugjung.googlemap.sample.map

import android.view.View
import androidx.annotation.IdRes

data class MapParams(@IdRes val resId: Int = View.NO_ID)

class MapParamsConfig {
    var resId: Int = View.NO_ID

    fun mapContentId(body: () -> Int) {
        this.resId = body()
    }
}

fun mapParams(body: MapParamsConfig.() -> Unit): MapParams {
    val config = MapParamsConfig().apply(body)
    return MapParams(config.resId)
}