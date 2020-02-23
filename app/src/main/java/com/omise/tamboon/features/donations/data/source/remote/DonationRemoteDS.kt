package com.omise.tamboon.features.donations.data.source.remote

import com.omise.tamboon.features.donations.data.source.remote.network.DonationApis
import com.omise.tamboon.features.donations.domain.entity.param.DonationRequestParam
import javax.inject.Inject

class DonationRemoteDS @Inject constructor(private val api : DonationApis) {

    fun  donate(donationParam : DonationRequestParam)=
         api.donate(donationParam)

}