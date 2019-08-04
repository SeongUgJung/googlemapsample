package com.seongugjung.googlemap.sample.view.mapinteractors

import com.seongugjung.googlemap.sample.location.LatLong
import com.seongugjung.googlemap.sample.map.Map
import com.seongugjung.googlemap.sample.map.MapInteractor
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject

class CenterInteractor : MapInteractor {

    private val centerLatLong = BehaviorSubject.createDefault(SINGAPORE)

    fun setCenter() {
        centerLatLong.onNext(SINGAPORE)
    }

    override fun onVisible(map: Map): Completable =
        centerLatLong.switchMap {
            map.zoomToLatLong(true, it)
        }.ignoreElements()

    companion object {

        @JvmStatic
        val SINGAPORE = LatLong(1.277236, 103.8524089)
    }
}