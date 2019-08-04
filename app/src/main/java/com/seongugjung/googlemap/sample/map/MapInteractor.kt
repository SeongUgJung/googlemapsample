package com.seongugjung.googlemap.sample.map

import io.reactivex.Completable

interface MapInteractor {
    fun onInit(map: Map): Completable = Completable.complete()
    fun onVisible(map: Map): Completable = Completable.complete()
}