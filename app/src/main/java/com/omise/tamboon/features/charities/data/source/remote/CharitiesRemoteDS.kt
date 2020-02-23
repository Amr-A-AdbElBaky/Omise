package com.omise.tamboon.features.charities.data.source.remote

import com.omise.tamboon.features.charities.data.model.Charity
import com.omise.tamboon.features.charities.data.source.remote.network.CharitiesApis
import io.reactivex.Single
import javax.inject.Inject

class CharitiesRemoteDS @Inject constructor(private val api : CharitiesApis) {

    fun getCharitiesList():Single<List<Charity>>{
        return api.getCharitiesList().map { it.charities}
    }
}