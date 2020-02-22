package com.omise.tamboon.features.donations.domain.entity

data class DonationEntity(
    val success: Boolean,
    val errorCode: String?,
    val errorMessage: String?
)