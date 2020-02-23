package com.omise.tamboon.base.presentation.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseViewModel: ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }



}