package com.omise.tamboon.features.donations.domain.entity.param

data class DonationRequestParam(
    var name: String,
    var token: String,
    var amount: String
)