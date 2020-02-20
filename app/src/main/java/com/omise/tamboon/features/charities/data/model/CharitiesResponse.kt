package com.omise.tamboon.features.charities.data.model

import com.google.gson.annotations.SerializedName

data class CharitiesResponse(
    val total:Int,
    @SerializedName("data")
    val charities :List<Charity>
)

data class Charity(
    val id : Long,
    val name :String,
    @SerializedName("logo_url")
    val logoUrl :String
)
