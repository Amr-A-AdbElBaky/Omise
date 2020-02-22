package com.omise.tamboon.core.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText


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

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.onActionDone(cb: () -> Unit) {
    this.setOnEditorActionListener { v, actionId, event ->
        if(actionId == EditorInfo.IME_ACTION_DONE){
            cb()
            true
        } else {
            false
        }
    }
}

fun EditText.getStringText():String{
    return this.text.toString()
}






