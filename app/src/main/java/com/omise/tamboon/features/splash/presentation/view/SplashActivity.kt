package com.omise.tamboon.features.splash.presentation.view

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.omise.tamboon.R
import com.omise.tamboon.features.charities.presentation.view.CharitiesActivity

class SplashActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        navigateToAppWithDelay()
    }

    private fun navigateToAppWithDelay() {
        Handler().postDelayed({
            CharitiesActivity.startMe(this)
        },
            3000)
    }
}