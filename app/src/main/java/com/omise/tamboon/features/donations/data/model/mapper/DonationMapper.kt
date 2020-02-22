package com.omise.tamboon.features.donations.data.model.mapper

import com.omise.tamboon.features.donations.data.model.DonationResponse
import com.omise.tamboon.features.donations.domain.entity.DonationEntity


fun DonationResponse.toEntity() = DonationEntity(
    success,
    errorCode,
    errorMessage
)