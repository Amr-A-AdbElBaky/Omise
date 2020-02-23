package com.omise.tamboon.features.donations.domain.repository

import com.omise.tamboon.features.donations.domain.entity.DonationEntity
import com.omise.tamboon.features.donations.domain.entity.param.DonationRequestParam
import io.reactivex.Single

interface DonationRepository {
    fun donate(donationParam : DonationRequestParam) :Single<DonationEntity>
}