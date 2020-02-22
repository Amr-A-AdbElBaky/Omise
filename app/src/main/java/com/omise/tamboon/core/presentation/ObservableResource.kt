package com.omise.tamboon.core.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.omise.tamboon.core.data.exception.CustomException


class ObservableResource<T> : ReplayLiveEvent<T>() {
    val error: SingleLiveEvent<CustomException> = ErrorLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    override fun removeObservers(owner: LifecycleOwner) {
        super.removeObservers(owner)
        loading.removeObservers(owner)
        error.removeObservers(owner)
    }

    fun observe(
        owner: LifecycleOwner,
        doOnSuccess: (T) -> Unit,
        doOnLoading: (() -> Unit)? = null,
        doOnError: (CustomException) -> Unit = { },
        doOnHttpError: ((CustomException) -> Unit)? = null,
        doOnNetworkError: ((CustomException) -> Unit)? = null,
        doOnUnExpectedError: ((CustomException) -> Unit)? = null,
        doOnTerminate: (() -> Unit)? = null
    ) {
        super.observe(owner, Observer {
            it?.let {
                doOnSuccess.invoke(it)
            } /*?: doOnComplete?.invoke()*/
        })

        loading.observe(owner, Observer { isLoading ->
            if (isLoading)
                doOnLoading?.invoke()
            else
                doOnTerminate?.invoke()
        })

        (error as ErrorLiveData).observe(owner,
            Observer { doOnError.invoke(it) },
            doOnHttpError?.let { Observer(doOnHttpError::invoke) },
            doOnNetworkError?.let { Observer(doOnNetworkError::invoke) },
            doOnUnExpectedError?.let { Observer(doOnUnExpectedError::invoke) })
    }

    class ErrorLiveData : SingleLiveEvent<CustomException>() {
        private var ownerRef: LifecycleOwner? = null
        private var httpErrorConsumer: Observer<CustomException>? = null
        private var networkErrorConsumer: Observer<CustomException>? = null
        private var unExpectedErrorConsumer: Observer<CustomException>? = null
        private var commonErrorConsumer: Observer<in CustomException>? = null
        override fun setValue(value: CustomException?) {
            ownerRef?.also {
                removeObservers(it)
                addProperObserver(value)
                super.setValue(value)
            }
        }

        override fun postValue(value: CustomException) {
            ownerRef?.also {
                removeObservers(it)
                addProperObserver(value)
                super.postValue(value)
            }
        }

        private fun addProperObserver(value: CustomException?) {
            when (value?.kind) {
                CustomException.Kind.NETWORK -> networkErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)
                CustomException.Kind.HTTP -> httpErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)
                CustomException.Kind.UNEXPECTED -> unExpectedErrorConsumer?.let {
                    observe(
                        ownerRef!!,
                        it
                    )
                }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)
                else -> {
                }
            }
        }


        fun observe(
            owner: LifecycleOwner,
            commonErrorConsumer: Observer<in CustomException>,
            httpErrorConsumer: Observer<CustomException>? = null,
            networkErrorConsumer: Observer<CustomException>? = null,
            unExpectedErrorConsumer: Observer<CustomException>? = null
        ) {
            super.observe(owner, commonErrorConsumer)
            this.ownerRef = owner
            this.commonErrorConsumer = commonErrorConsumer
            this.httpErrorConsumer = httpErrorConsumer
            this.networkErrorConsumer = networkErrorConsumer
            this.unExpectedErrorConsumer = unExpectedErrorConsumer
        }
    }
}