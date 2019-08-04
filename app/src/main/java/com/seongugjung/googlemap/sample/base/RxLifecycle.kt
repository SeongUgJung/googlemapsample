package com.seongugjung.googlemap.sample.base

import android.os.SystemClock
import io.reactivex.disposables.Disposable
import timber.log.Timber

typealias DisposableFunction = () -> Disposable

fun onInit(disposableFunction: DisposableFunction): RxLifecycle =
    RxLifecycle().init {
        val time: Long = SystemClock.elapsedRealtime()
        disposableFunction().apply {
            Timber.d("Duration : %d", SystemClock.elapsedRealtime() - time)
        }
    }


fun onVisible(disposableFunction: DisposableFunction): RxLifecycle =
    RxLifecycle().visible {
        val time: Long = SystemClock.elapsedRealtime()
        disposableFunction().apply {
            Timber.d("Duration : %d", SystemClock.elapsedRealtime() - time)
        }
    }

class RxLifecycle : LifecycleObserver {

    private var onInit: DisposableFunction? = null
    private var initDisposable: Disposable? = null
    private var onVisible: DisposableFunction? = null
    private var visibleDisposable: Disposable? = null

    fun init(init: DisposableFunction): RxLifecycle {
        this.onInit = init
        return this
    }

    fun visible(visible: DisposableFunction): RxLifecycle {
        this.onVisible = visible
        return this
    }

    override fun onInit() {
        initDisposable = this.onInit?.invoke()
    }

    override fun onVisible() {
        visibleDisposable = this.onVisible?.invoke()
    }

    override fun onInvisible() {
        if (visibleDisposable?.isDisposed == false) {
            visibleDisposable?.dispose()
        }
    }

    override fun onDeinit() {
        if (initDisposable?.isDisposed == false) {
            initDisposable?.dispose()
        }
    }
}