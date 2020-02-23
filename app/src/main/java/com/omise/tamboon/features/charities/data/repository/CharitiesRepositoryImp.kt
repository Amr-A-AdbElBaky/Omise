package com.omise.tamboon.features.charities.data.repository

import com.omise.tamboon.features.charities.data.model.mapper.toEntity
import com.omise.tamboon.features.charities.data.source.remote.CharitiesRemoteDS
import com.omise.tamboon.features.charities.domain.repository.CharitiesRepository
import javax.inject.Inject

class CharitiesRepositoryImp @Inject constructor( private val charitiesRemoteDS: CharitiesRemoteDS) : CharitiesRepository {
    override fun getCharitiesList()=
         charitiesRemoteDS.getCharitiesList().map {
            it.map { charity ->  charity.toEntity() }

    }


}