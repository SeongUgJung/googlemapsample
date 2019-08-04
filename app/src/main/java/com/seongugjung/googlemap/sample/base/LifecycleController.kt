package com.seongugjung.googlemap.sample.base

open class LifecycleController : RxLifecycleDelegate {

    private val observers = mutableListOf<LifecycleObserver>()
    private var state: LifecycleState = Unknown

    override fun LifecycleObserver.unaryPlus() {
        this@LifecycleController += this
    }

    operator fun plusAssign(observer: LifecycleObserver) {
        observers += observer

        when (state) {
            Init -> observer.onInit()
            Deinit -> observer.onDeinit()
            Visible -> observer.onVisible()
            Invisible -> observer.onInvisible()
        }

    }

    fun onInit() {
        observers.forEach { it.onInit() }
        state = Init
    }

    fun onVisible() {
        observers.forEach { it.onVisible() }
        state = Visible
    }

    fun onInvisible() {
        observers.forEach { it.onInvisible() }
        state = Invisible
    }

    fun onDeinit() {
        observers.forEach { it.onDeinit() }
        state = Deinit
    }
}

interface RxLifecycleDelegate {
    operator fun LifecycleObserver.unaryPlus()
}

interface LifecycleObserver {
    fun onInit() = Unit
    fun onVisible() = Unit
    fun onInvisible() = Unit
    fun onDeinit() = Unit
}

sealed class LifecycleState
object Unknown : LifecycleState()
object Init : LifecycleState()
object Deinit : LifecycleState()
object Visible : LifecycleState()
object Invisible : LifecycleState()