package com.omise.tamboon.features.charities.data.source.api

import com.omise.tamboon.features.charities.data.model.CharitiesResponse
import io.reactivex.Single
import retrofit2.http.GET

interface CharitiesApis {

    @GET("chakritw/tamboon-api/1.0.0/charities")
    fun getCharitiesList():Single<CharitiesResponse>
}