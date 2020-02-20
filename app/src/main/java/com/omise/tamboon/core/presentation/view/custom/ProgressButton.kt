package com.omise.tamboon.core.presentation.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.omise.tamboon.R
import com.omise.tamboon.core.extensions.gone
import com.omise.tamboon.core.extensions.visible
import kotlinx.android.synthetic.main.layout_progress_button.view.*

class ProgressButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        addView(View.inflate(context, R.layout.layout_progress_button, null))
        init(attrs)
    }

    var label: String = ""
        set(value) {
            tvProgressButton?.text = value
            field = value
        }

    fun getLabelTextView(): TextView? = tvProgressButton

    private fun init(set: AttributeSet?) {
        val ta = context.obtainStyledAttributes(set, R.styleable.ProgressButton)

        val progressText = ta.getString(R.styleable.ProgressButton_progress_text)
        val clProgressButtonBackground = ta.getColor(R.styleable.ProgressButton_background_color, ContextCompat.getColor(context, R.color.colorOceanBlue))
        val pgProgressColor = ta.getColor(R.styleable.ProgressButton_progress_color, ContextCompat.getColor(context, R.color.colorWhite))
        val tvProgressButtonTextColor = ta.getColor(R.styleable.ProgressButton_text_color, ContextCompat.getColor(context, R.color.colorWhite))
        val progressSize = ta.getInt(R.styleable.ProgressButton_progress_size, 25)
        val tvDrawable = ta.getDrawable(R.styleable.ProgressButton_progress_icon)


      //  clProgressButton.setBackgroundColor( clProgressButtonBackground)
        with(pgProgressButton){
            layoutParams.width = progressSize
            layoutParams.height = progressSize

        }

        with(tvProgressButton){
            text = progressText
            setTextColor( tvProgressButtonTextColor)
            setCompoundDrawablesRelativeWithIntrinsicBounds(tvDrawable, null, null, null)
        }
    }

    fun startProgress(){
        pgProgressButton.visible()
        tvProgressButton.gone()
    }
    fun isLoading() = pgProgressButton.visibility  == View.VISIBLE

    fun stopProgress(){
        pgProgressButton.gone()
        tvProgressButton.visible()
    }

}