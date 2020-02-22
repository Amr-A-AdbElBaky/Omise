package com.omise.tamboon.features.donations.domain.interactor

import com.omise.tamboon.base.domain.interactor.SingleUseCase
import com.omise.tamboon.features.charities.domain.entity.CharityEntity
import com.omise.tamboon.features.charities.domain.repository.CharitiesRepository
import com.omise.tamboon.features.donations.domain.entity.DonationEntity
import com.omise.tamboon.features.donations.domain.repository.DonationRepository
import com.omise.tamboon.features.donations.presentation.param.DonationRequestParam
import io.reactivex.Single
import javax.inject.Inject

class DonateUseCase @Inject constructor(private val charitiesRepository: DonationRepository) :SingleUseCase<DonationRequestParam , DonationEntity>() {
    override fun build(params: DonationRequestParam): Single<DonationEntity> {
        return charitiesRepository.donate(params)
    }
}