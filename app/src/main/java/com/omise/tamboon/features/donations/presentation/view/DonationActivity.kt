package com.omise.tamboon.features.donations.presentation.view

import android.app.Activity
import android.content.Intent
import com.omise.tamboon.features.donations.presentation.param.DonationNavigationParam
import dagger.android.support.DaggerAppCompatActivity

class DonationActivity :DaggerAppCompatActivity() {





    companion object{
        private const val DONATION_KEY = "DonationParam"

        fun startMe(activity :Activity , donationNavigationParam: DonationNavigationParam){
            val intent = Intent(activity , DonationActivity::class.java)
            intent.putExtra(DONATION_KEY , donationNavigationParam)
        }

    }
}