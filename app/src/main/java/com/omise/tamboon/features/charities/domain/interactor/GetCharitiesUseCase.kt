package com.omise.tamboon.features.charities.domain.interactor

import com.omise.tamboon.base.domain.interactor.SingleUseCase
import com.omise.tamboon.features.charities.domain.entity.CharityEntity
import com.omise.tamboon.features.charities.domain.repository.CharitiesRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCharitiesUseCase @Inject constructor(private val charitiesRepository: CharitiesRepository) :SingleUseCase<Unit , List<CharityEntity>>() {

    override fun build(params: Unit)= charitiesRepository.getCharitiesList()

}