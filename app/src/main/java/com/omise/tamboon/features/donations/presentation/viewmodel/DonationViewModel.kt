package com.omise.tamboon.features.donations.presentation.viewmodel

import com.omise.tamboon.base.presentation.viewmodel.BaseViewModel
import com.omise.tamboon.core.extensions.addTo
import com.omise.tamboon.core.extensions.publishToObservableResource
import com.omise.tamboon.core.presentation.ObservableResource
import com.omise.tamboon.features.donations.domain.entity.DonationEntity
import com.omise.tamboon.features.donations.domain.interactor.DonateUseCase
import com.omise.tamboon.features.donations.presentation.param.DonationRequestParam
import javax.inject.Inject

class DonationViewModel @Inject constructor(
    private val donateUseCase : DonateUseCase
) :BaseViewModel() {

    val donatioRes = ObservableResource<DonationEntity>()

    fun donate(donationRequestParam: DonationRequestParam){
        donateUseCase.build(donationRequestParam).publishToObservableResource(donatioRes)
            .addTo(compositeDisposable)

    }
}