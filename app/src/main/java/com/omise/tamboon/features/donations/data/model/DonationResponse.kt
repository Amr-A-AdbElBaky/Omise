package com.omise.tamboon.features.donations.data.model

import com.google.gson.annotations.SerializedName

class DonationResponse (
    val success :Boolean,
    @SerializedName("error_code")val errorCode :String?,
    @SerializedName("error_message")val errorMessage :String?
)