package com.omise.tamboon.features.donations.data.repository

import com.omise.tamboon.features.donations.data.model.mapper.toEntity
import com.omise.tamboon.features.donations.data.source.remote.DonationRemoteDS
import com.omise.tamboon.features.donations.domain.repository.DonationRepository
import com.omise.tamboon.features.donations.domain.entity.param.DonationRequestParam
import javax.inject.Inject

class DonationRepositoryImp @Inject constructor(private val donationRemoteDS: DonationRemoteDS) : DonationRepository {
    override fun donate(donationParam: DonationRequestParam)=
         donationRemoteDS.donate(donationParam).map { it.toEntity() }



}