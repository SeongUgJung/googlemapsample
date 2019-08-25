package com.seongugjung.googlemap.sample

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.seongugjung.googlemap.sample.di.ApplicationComponent
import com.seongugjung.googlemap.sample.di.DaggerApplicationComponent
import timber.log.Timber

class MapApplication : MultiDexApplication() {

    private lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
            .application(this)
            .build().apply { inject(this@MapApplication) }

        Timber.plant(Timber.DebugTree())

    }

    fun <T> dependency(): T {
        return component as T
    }

}

fun <T> Context.dependecy(): T {
    return (applicationContext as MapApplication).dependency()
}