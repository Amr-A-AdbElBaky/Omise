package com.omise.tamboon.features.charities.domain.repository

import com.omise.tamboon.features.charities.domain.entity.CharityEntity
import io.reactivex.Single

interface CharitiesRepository {

    fun getCharitiesList() :Single<List<CharityEntity>>
}