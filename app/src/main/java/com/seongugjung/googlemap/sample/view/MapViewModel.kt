package com.seongugjung.googlemap.sample.view

import com.seongugjung.googlemap.sample.base.RxLifecycleDelegate
import com.seongugjung.googlemap.sample.view.mapinteractors.CenterInteractor

class MapViewModel(
    rxLifecycleDelegate: RxLifecycleDelegate,
    private val centerInteractor: CenterInteractor
) :
    RxLifecycleDelegate by rxLifecycleDelegate {

    fun moveCenter() {
        centerInteractor.setCenter()
    }
}