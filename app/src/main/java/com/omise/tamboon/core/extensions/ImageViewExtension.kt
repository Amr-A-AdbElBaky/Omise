package com.omise.tamboon.core.extensions

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.omise.tamboon.core.di.module.GlideApp
import com.omise.tamboon.core.di.module.GlideRequest


fun ImageView.loadImage(@DrawableRes resourceId: Int, placeholder: Int? = null, trans: MultiTransformation<Bitmap>? = null, listener: MutableLiveData<Bitmap?>? = null) {
    val glideApp = GlideApp.with(context)
            .asBitmap()
            .load(resourceId)

    loadImageOptions(glideApp = glideApp , placeholder = placeholder , trans = trans , listener = listener)
}

fun ImageView.loadImage(url: String, placeholder: Int? = null, trans: MultiTransformation<Bitmap>? = null, listener: MutableLiveData<Bitmap?>? = null) {
    val glideApp = GlideApp.with(context)
            .asBitmap()
            .load(url)

    loadImageOptions(glideApp = glideApp , placeholder = placeholder , trans = trans , listener = listener)
}
fun ImageView.loadImageBlur(url: String, placeholder: Int? = null, trans: MultiTransformation<Bitmap>? = null, listener: MutableLiveData<Bitmap?>? = null) {
    val glideApp = GlideApp.with(context)
            .asBitmap()
            .load(url)

    loadImageOptions(glideApp = glideApp , placeholder = placeholder , trans = trans , listener = listener)
}

fun ImageView.bitmapTransformation(bitmap: Bitmap, trans: MultiTransformation<Bitmap>, listener: MutableLiveData<Bitmap?>? = null) {
    val glideApp = GlideApp.with(context)
            .asBitmap()
            .load(bitmap).apply(RequestOptions.bitmapTransform(trans))

    loadImageOptions(glideApp , trans = trans , listener = listener)
}


private fun ImageView.loadImageOptions(glideApp: GlideRequest<Bitmap>, placeholder: Int? = null, trans: MultiTransformation<Bitmap>? = null, listener: MutableLiveData<Bitmap?>? = null){
    placeholder?.let {
        glideApp.apply(RequestOptions().placeholder(placeholder))
    }
    trans?.let {
        glideApp.apply(RequestOptions.bitmapTransform(trans))
    }

    if (listener == null)
        glideApp.into(this)
    else
        glideApp.listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                listener.value = null
                return true
            }

            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                listener.value = resource
                return true
            }

        }).submit()
}



