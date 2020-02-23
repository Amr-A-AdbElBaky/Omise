package com.omise.tamboon.features.splash.presentation.view

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.omise.tamboon.R
import com.omise.tamboon.core.di.module.ViewModelFactory
import com.omise.tamboon.features.charities.presentation.view.CharitiesActivity
import com.omise.tamboon.features.donations.presentation.viewmodel.DonationViewModel
import com.omise.tamboon.features.splash.presentation.viewmodel.SplashViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SplashActivity :DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val mSplashViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mSplashViewModel.navigationLiveEvent.observe(this , Observer {
            CharitiesActivity.startMe(this)
        })

    }


}