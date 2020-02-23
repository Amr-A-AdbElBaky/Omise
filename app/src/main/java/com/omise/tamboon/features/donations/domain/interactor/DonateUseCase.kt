package com.omise.tamboon.features.donations.domain.interactor

import com.omise.tamboon.base.domain.interactor.SingleUseCase
import com.omise.tamboon.features.donations.domain.entity.DonationEntity
import com.omise.tamboon.features.donations.domain.repository.DonationRepository
import com.omise.tamboon.features.donations.domain.entity.param.DonationRequestParam
import javax.inject.Inject

class DonateUseCase @Inject constructor(private val charitiesRepository: DonationRepository) :SingleUseCase<DonationRequestParam, DonationEntity>() {
    override fun build(params: DonationRequestParam)=
         charitiesRepository.donate(params)

}