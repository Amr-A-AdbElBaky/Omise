package com.omise.tamboon.features.donations.data.source

import com.omise.tamboon.features.donations.data.model.DonationResponse
import com.omise.tamboon.features.donations.data.source.api.DonationApis
import com.omise.tamboon.features.donations.presentation.param.DonationRequestParam
import io.reactivex.Single
import javax.inject.Inject

class DonationRemoteDS @Inject constructor(private val api : DonationApis) {

    fun  donate(donationParam :DonationRequestParam):Single<DonationResponse>{
        return api.donate(donationParam)
    }
}