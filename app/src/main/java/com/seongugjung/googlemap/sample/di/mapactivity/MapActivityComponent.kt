package com.seongugjung.googlemap.sample.di.mapactivity

import androidx.fragment.app.FragmentManager
import com.seongugjung.googlemap.sample.R
import com.seongugjung.googlemap.sample.base.RxLifecycleDelegate
import com.seongugjung.googlemap.sample.base.resources.ResourceProvider
import com.seongugjung.googlemap.sample.base.rx.SchedulerManager
import com.seongugjung.googlemap.sample.di.ActivityScope
import com.seongugjung.googlemap.sample.map.MapController
import com.seongugjung.googlemap.sample.map.MapProvider
import com.seongugjung.googlemap.sample.map.google.GoogleMapProvider
import com.seongugjung.googlemap.sample.map.mapParams
import com.seongugjung.googlemap.sample.view.MapActivity
import com.seongugjung.googlemap.sample.view.MapViewModel
import com.seongugjung.googlemap.sample.view.mapinteractors.CenterInteractor
import com.seongugjung.googlemap.sample.view.mapinteractors.MarkerInteractor
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@ActivityScope
@Component(
    modules = [MapActivityComponent.ActivityModule::class],
    dependencies = [MapActivityDependency::class]
)
interface MapActivityComponent {

    fun inject(activity: MapActivity)

    @Component.Builder
    interface Builder {
        fun build(): MapActivityComponent
        fun mapActivityRequire(require: MapActivityDependency): Builder

        @BindsInstance
        fun lifecycleDelegate(lifecycleDelegate: RxLifecycleDelegate): Builder

        @BindsInstance
        fun fragmentManager(fragmentManager: FragmentManager): Builder

    }

    @Module
    object ActivityModule {
        @JvmStatic
        @Provides
        @ActivityScope
        fun provideMapController(
            lifecycleDelegate: RxLifecycleDelegate,
            mapProvider: MapProvider,
            centerInteractor: CenterInteractor,
            markerInteractor: MarkerInteractor
        ): MapController {
            return MapController(lifecycleDelegate, mapProvider, centerInteractor, markerInteractor)
        }

        @JvmStatic
        @Provides
        @ActivityScope
        fun centerInteractor() = CenterInteractor()

        @JvmStatic
        @Provides
        @ActivityScope
        fun markerInteractor() = MarkerInteractor()

        @JvmStatic
        @Provides
        @ActivityScope
        fun mapViewModel(
            lifecycleDelegate: RxLifecycleDelegate,
            centerInteractor: CenterInteractor,
            markerInteractor: MarkerInteractor
        ): MapViewModel {
            return MapViewModel(lifecycleDelegate, centerInteractor, markerInteractor)
        }


        @JvmStatic
        @Provides
        @ActivityScope
        fun provideMapProvider(
            fragmentManager: FragmentManager,
            schedulerManager: SchedulerManager,
            resourceProvider: ResourceProvider
        ): MapProvider {
            return GoogleMapProvider(
                fragmentManager,
                mapParams {
                    mapContentId { R.id.map }
                }, resourceProvider, schedulerManager
            )
        }

    }
}

interface MapActivityDependency {
    fun rxSchedulerManager(): SchedulerManager
    fun resourceProvider(): ResourceProvider
}