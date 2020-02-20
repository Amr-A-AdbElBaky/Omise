package com.omise.tamboon.features.charities.data.repository

import com.omise.tamboon.features.charities.data.model.mapper.toEntity
import com.omise.tamboon.features.charities.data.source.CharitiesRemoteDS
import com.omise.tamboon.features.charities.domain.entity.CharityEntity
import com.omise.tamboon.features.charities.domain.repository.CharitiesRepository
import io.reactivex.Single
import javax.inject.Inject

class CharitiesRepositoryImp @Inject constructor( private val charitiesRemoteDS: CharitiesRemoteDS) : CharitiesRepository {
    override fun getCharitiesList(): Single<List<CharityEntity>> {
        return charitiesRemoteDS.getCharitiesList().map {
            it.map { it.toEntity() }
        }
    }


}