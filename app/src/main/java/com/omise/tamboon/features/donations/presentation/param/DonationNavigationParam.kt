package com.omise.tamboon.features.donations.presentation.param

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DonationNavigationParam (
    var charityName :String,
    var userName :String ,
    var token :String
):Parcelable