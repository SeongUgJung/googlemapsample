package com.seongugjung.googlemap.sample.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity : AppCompatActivity(), RxLifecycleDelegate {

    private val lifecycleController = LifecycleController()

    override fun LifecycleObserver.unaryPlus() {
        lifecycleController += this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDataBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, layout())
        lifecycleController.onInit()
        inject()
        databinding(viewDataBinding)
    }


    @LayoutRes
    abstract fun layout(): Int

    abstract fun inject()

    abstract fun databinding(viewDataBinding: ViewDataBinding)


    override fun onResume() {
        super.onResume()
        lifecycleController.onVisible()
    }

    override fun onPause() {
        super.onPause()
        lifecycleController.onInvisible()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleController.onDeinit()
    }
}