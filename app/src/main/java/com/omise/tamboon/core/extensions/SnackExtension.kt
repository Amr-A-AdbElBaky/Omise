package  com.omise.tamboon.core.extensions

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import com.google.android.material.snackbar.Snackbar
import com.omise.tamboon.R


fun View.showSnack(message: String, @ColorRes backgroundColor: Int = R.color.colorAlertRed, length: Int = 4000, doAction: Snackbar.() -> Unit = {}): Snackbar {
    return getSnack(message = message, backgroundColor=backgroundColor,length = length,doAction = doAction)
}

fun View.getSuccessSnack( message :String, length: Int = 4000, doAction: Snackbar.() -> Unit = {}) :Snackbar {
    return showSnack(message,backgroundColor = R.color.colorSuccessGreen)
}

fun View.getErrorSnack( message :String, length: Int = 4000, doAction: Snackbar.() -> Unit = {}) :Snackbar {
    return showSnack(message)
}
fun View.getSnackNetworkError(): Snackbar {
    return this.getSnack(context.resources.getString(R.string.screens_error_messages_internet_down))
}

fun View.getSnackUnExpectedError(): Snackbar {
    return this.getSnack(context.resources.getString(R.string.screens_error_messages_unExpected))
}


fun View.getSnack(message: String, @ColorRes backgroundColor: Int = R.color.colorAlertRed, length: Int = 4000,
                   doAction: Snackbar.() -> Unit = {}, gravity: Int = Gravity.BOTTOM): Snackbar {
    val snack = Snackbar.make(this, message.replace("\n",""), length)
    snack.doAction()
    with(snack.view) {
        val params = layoutParams as FrameLayout.LayoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.gravity = gravity
        layoutParams = params
        setBackgroundResource(backgroundColor)
    }


    val mTextView = snack.view.findViewById(R.id.snackbar_text) as TextView
    mTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    mTextView.maxLines=1000
    // snack.show()
    return snack
}



