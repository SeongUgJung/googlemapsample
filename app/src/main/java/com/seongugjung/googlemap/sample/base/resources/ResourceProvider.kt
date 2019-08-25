package com.seongugjung.googlemap.sample.base.resources

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources

interface ResourceProvider {
    fun getDrawable(@DrawableRes resId: Int): Drawable?
    fun getDimen(@DimenRes resId: Int): Int
}

class ResourceProviderImpl(private val context: Context) : ResourceProvider {
    private val resources by lazy { context.resources }
    override fun getDrawable(resId: Int) = AppCompatResources.getDrawable(context, resId)

    override fun getDimen(resId: Int): Int = resources.getDimensionPixelSize(resId)
}