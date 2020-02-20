package com.omise.tamboon.core.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


fun ViewGroup.inflate(layout: Int) {
    LayoutInflater.from(context).inflate(layout, this)

}

fun ViewGroup.getInflatedView(layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}

fun LayoutInflater.getInflatedView(layout: Int, container: ViewGroup?): View {
    return inflate(layout, container, false)
}



infix fun View?.onClick(action: (() -> Unit)?) {
    this?.setOnClickListener { action?.invoke() }
}

fun View.setMarginStart(marginStart: Int) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.marginStart = marginStart
        requestLayout()
    }
}
fun View.setHeight(heightInPx:Int){
    val newParams = layoutParams
    newParams.height=heightInPx
    layoutParams=newParams
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.disable(withAlpha: Boolean = true) {
    this.isEnabled = false
    if (withAlpha) this.alpha = 0.5f
}

fun View.enable(withAlpha: Boolean = true) {
    this.isEnabled = true
    if (withAlpha) this.alpha = 1.0f
}

fun Button.setVectorDrawableEnd(resourceId: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, context.setVectorForPreLollipop(resourceId), null)
}





