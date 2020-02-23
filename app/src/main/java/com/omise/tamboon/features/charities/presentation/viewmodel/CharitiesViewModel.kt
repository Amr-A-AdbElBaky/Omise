package com.omise.tamboon.features.charities.presentation.viewmodel

import com.omise.tamboon.base.presentation.viewmodel.BaseViewModel
import com.omise.tamboon.core.extensions.addTo
import com.omise.tamboon.core.extensions.publishListToObservableResource
import com.omise.tamboon.core.presentation.ObservableResource
import com.omise.tamboon.features.charities.domain.entity.CharityEntity
import com.omise.tamboon.features.charities.domain.interactor.GetCharitiesUseCase
import javax.inject.Inject

class CharitiesViewModel @Inject constructor(
    private val getCharitiesUseCase: GetCharitiesUseCase
) :BaseViewModel() {

    val charitiesResource = ObservableResource<List<CharityEntity>>()

    fun requestCharities(){
        getCharitiesUseCase.build(Unit).publishListToObservableResource(charitiesResource)
            .addTo(compositeDisposable)

    }
}