package com.seongugjung.googlemap.sample.map

import com.seongugjung.googlemap.sample.base.RxLifecycleDelegate
import com.seongugjung.googlemap.sample.base.onInit
import com.seongugjung.googlemap.sample.base.onVisible
import io.reactivex.Observable

class MapController(
    private val lifecycleDelegate: RxLifecycleDelegate,
    private val mapProvider: MapProvider,
    private vararg val mapInteractors: MapInteractor
) : RxLifecycleDelegate by lifecycleDelegate {

    init {
        +onInit {
            mapProvider.initMap()
                .switchMapCompletable { map ->
                    Observable.fromIterable(mapInteractors.asIterable())
                        .flatMapCompletable { mapInteractor ->
                            mapInteractor.onInit(map)
                        }
                }
                .subscribe()
        }

        +onVisible {
            mapProvider.getMap()
                .switchMapCompletable { map ->
                    Observable.fromIterable(mapInteractors.asIterable())
                        .flatMapCompletable { mapInteractor ->
                            mapInteractor.onVisible(map)
                        }
                }
                .subscribe()
        }
    }

}