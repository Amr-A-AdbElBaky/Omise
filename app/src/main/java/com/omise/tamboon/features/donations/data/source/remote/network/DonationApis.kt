package com.omise.tamboon.features.donations.data.source.remote.network

import com.omise.tamboon.features.donations.data.model.DonationResponse
import com.omise.tamboon.features.donations.domain.entity.param.DonationRequestParam
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface DonationApis {

    @POST("chakritw/tamboon-api/1.0.0/donations")
    fun donate(@Body donationParam : DonationRequestParam):Single<DonationResponse>
}