package com.seongugjung.googlemap.sample.base.rx

import android.os.Looper
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerManager {
    fun ui(): Scheduler = AndroidSchedulers.from(Looper.getMainLooper(), true)
    fun computation(): Scheduler = Schedulers.computation()
    fun io(): Scheduler = Schedulers.io()
}