package com.omise.tamboon.core.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText

fun ViewGroup.getInflatedView(layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}

infix fun View?.onClick(action: (() -> Unit)?) {
    this?.setOnClickListener { action?.invoke() }
}

fun View.visible() {
    this.visibility = View.VISIBLE
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






