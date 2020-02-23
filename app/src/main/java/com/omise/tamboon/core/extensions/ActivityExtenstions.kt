package com.omise.tamboon.core.extensions

import android.app.Activity
import android.app.Dialog
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.omise.tamboon.R
import kotlinx.android.synthetic.main.dialog_success.view.*
import kotlinx.android.synthetic.main.layout_toolbar.*


fun Activity.closeKeyboard() {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun AppCompatActivity.initToolBar(title: String? = null, enableBack: Boolean) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayShowTitleEnabled(false)
    supportActionBar?.setDefaultDisplayHomeAsUpEnabled(false)
    title?.let {
        tvToolbarTitle.text = title
    }
    if (enableBack)
        imgToolbar.loadImage(R.drawable.ic_arrow_back)
    else
        imgToolbar.gone()

    imgToolbar.setOnClickListener {
        onBackPressed()
    }
}


fun AppCompatActivity.createAlertDialog(
    title: String? = null, message: String, positiveText: String = getString(R.string.label_done),
    positiveButtonListener: ((dialog : Dialog) -> Unit?)? = null, icon: Int?= null , isCancelable :Boolean = false){

    val alertBuilder = AlertDialog.Builder(this)
    var  dialog :Dialog? = null
    val  dialogView = this.layoutInflater.inflate(R.layout.dialog_success, null)
    alertBuilder.setView(dialogView)


    icon?.let {
        dialogView.imgSuccess.loadImage(it)
        dialogView.imgSuccess.visible()
    }?:run{
        dialogView.imgSuccess.gone()
    }
    title?.let {
        dialogView.tvSuccess.visible()
        dialogView.tvSuccess.text = it
    }?:run{
        dialogView.tvSuccess.gone()
    }

    dialogView.tvMessage.text = message
    dialogView.tvOk.text = positiveText
    dialogView.tvOk.setOnClickListener {
        positiveButtonListener?.invoke(dialog!!) ?:run {
            dialog?.dismiss()
        }
    }

    dialog = alertBuilder.create()
    dialog.setCancelable(isCancelable)
    dialog.window!!.setBackgroundDrawableResource(R.drawable.shape_white_32dp_corners)
    dialog.show()
}

