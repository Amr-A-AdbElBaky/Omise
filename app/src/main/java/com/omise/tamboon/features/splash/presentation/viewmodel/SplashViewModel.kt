package com.omise.tamboon.features.splash.presentation.viewmodel

import com.omise.tamboon.base.presentation.viewmodel.BaseViewModel
import com.omise.tamboon.core.extensions.addTo
import com.omise.tamboon.core.presentation.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashViewModel @Inject constructor() : BaseViewModel() {

    val navigationLiveEvent = SingleLiveEvent<Boolean>()

    init {
        Observable.timer(2, TimeUnit.SECONDS, Schedulers.io()).subscribe({
            navigationLiveEvent.postValue( true)
        }, {}).addTo(compositeDisposable)


    }

}