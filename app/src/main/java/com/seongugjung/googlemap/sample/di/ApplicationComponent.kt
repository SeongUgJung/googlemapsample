package com.seongugjung.googlemap.sample.di

import com.seongugjung.googlemap.sample.MapApplication
import com.seongugjung.googlemap.sample.base.rx.SchedulerManager
import com.seongugjung.googlemap.sample.di.mapactivity.MapActivityDependency
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationComponent.ApplicationModule::class])
interface ApplicationComponent : MapActivityDependency {
    fun inject(application: MapApplication)

    @Module
    object ApplicationModule {
        @JvmStatic
        @Provides
        @Singleton
        fun rxSchedulerManager(): SchedulerManager = object : SchedulerManager {}
    }

}
