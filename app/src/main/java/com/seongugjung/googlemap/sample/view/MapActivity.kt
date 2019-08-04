package com.seongugjung.googlemap.sample.view

import androidx.databinding.ViewDataBinding
import com.seongugjung.googlemap.sample.BR
import com.seongugjung.googlemap.sample.R
import com.seongugjung.googlemap.sample.base.BaseActivity
import com.seongugjung.googlemap.sample.dependecy
import com.seongugjung.googlemap.sample.di.mapactivity.DaggerMapActivityComponent
import com.seongugjung.googlemap.sample.map.MapController
import javax.inject.Inject


class MapActivity : BaseActivity() {

    @Inject
    lateinit var mapController: MapController

    @Inject
    lateinit var mapViewModel: MapViewModel

    override fun layout(): Int = R.layout.activity_map

    override fun inject() {
        DaggerMapActivityComponent
            .builder()
            .lifecycleDelegate(this)
            .fragmentManager(supportFragmentManager)
            .mapActivityRequire(dependecy())
            .build()
            .inject(this)
    }

    override fun databinding(viewDataBinding: ViewDataBinding) {
        viewDataBinding.setVariable(BR.viewModel, mapViewModel)
    }
}