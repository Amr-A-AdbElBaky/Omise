package com.omise.tamboon.features.donations.data.repository

import com.omise.tamboon.features.donations.data.model.mapper.toEntity
import com.omise.tamboon.features.donations.data.source.DonationRemoteDS
import com.omise.tamboon.features.donations.domain.entity.DonationEntity
import com.omise.tamboon.features.donations.domain.repository.DonationRepository
import com.omise.tamboon.features.donations.presentation.param.DonationRequestParam
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DonationRepositoryImp @Inject constructor(private val donationRemoteDS: DonationRemoteDS) : DonationRepository {
    override fun donate(donationParam: DonationRequestParam): Single<DonationEntity> {
        return donationRemoteDS.donate(donationParam).map { it.toEntity() }
    }


}