package com.omise.tamboon.features.charities.data.source

import com.omise.tamboon.features.charities.data.model.Charity
import com.omise.tamboon.features.charities.data.source.api.CharitiesApis
import io.reactivex.Single
import javax.inject.Inject

class CharitiesRemoteDS @Inject constructor(private val api : CharitiesApis) {

    fun getCharitiesList():Single<List<Charity>>{
        return api.getCharitiesList().map { it.charities}
    }
}