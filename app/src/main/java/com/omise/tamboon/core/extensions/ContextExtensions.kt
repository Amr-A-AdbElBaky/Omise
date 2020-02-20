package com.omise.tamboon.core.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

@SuppressLint("ObsoleteSdkInt")
fun Context.setVectorForPreLollipop(resourceId: Int): Drawable? {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        VectorDrawableCompat.create(resources, resourceId, null)
    } else {
        resources.getDrawable(resourceId, null)
    }
}