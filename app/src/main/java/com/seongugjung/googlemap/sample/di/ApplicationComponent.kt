package com.seongugjung.googlemap.sample.di

import android.app.Application
import com.seongugjung.googlemap.sample.MapApplication
import com.seongugjung.googlemap.sample.base.resources.ResourceProvider
import com.seongugjung.googlemap.sample.base.resources.ResourceProviderImpl
import com.seongugjung.googlemap.sample.base.rx.SchedulerManager
import com.seongugjung.googlemap.sample.di.mapactivity.MapActivityDependency
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationComponent.ApplicationModule::class])
interface ApplicationComponent : MapActivityDependency {
    fun inject(application: MapApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent

    }

    @Module
    object ApplicationModule {
        @JvmStatic
        @Provides
        @Singleton
        fun rxSchedulerManager(): SchedulerManager = object : SchedulerManager {}

        @JvmStatic
        @Provides
        @Singleton
        fun resourceProvider(application: Application): ResourceProvider =
            ResourceProviderImpl(application)
    }

}
