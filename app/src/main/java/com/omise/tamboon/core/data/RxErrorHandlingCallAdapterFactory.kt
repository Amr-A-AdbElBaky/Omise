package com.omise.tamboon.core.data

import com.omise.tamboon.core.data.exception.CustomException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type


class RxErrorHandlingCallAdapterFactory: CallAdapter.Factory() {

    private val _original by lazy {
        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    companion object {
        fun create() : CallAdapter.Factory = RxErrorHandlingCallAdapterFactory()
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *> {
        val wrapped = _original.get(returnType, annotations, retrofit) as CallAdapter<out Any, *>
        return RxCallAdapterWrapper(retrofit, wrapped)
    }

    private class RxCallAdapterWrapper<R>(val _retrofit: Retrofit,
                                          val _wrappedCallAdapter: CallAdapter<R, *>
    ): CallAdapter<R, Any> {

        override fun responseType(): Type = _wrappedCallAdapter.responseType()


        @Suppress("UNCHECKED_CAST")
        override fun adapt(call: Call<R>): Any {
            return when (val result = _wrappedCallAdapter.adapt(call)) {
                is Single<*> -> result.onErrorResumeNext{ throwable -> Single.error(asCustomException(throwable)) }
                is Completable -> result.onErrorResumeNext{ throwable -> Completable.error(asCustomException(throwable)) }
                else -> result
            }
        }

        private fun asCustomException(throwable: Throwable): CustomException {
            // We had non-200 http error
            if (throwable is HttpException) {
                val response = throwable.response()
                return when {
                    throwable.code() == 492 ->
                        CustomException.userNotVerified( response )
                    throwable.code() == 401 ->
                        CustomException.notAuthorized( response)
                    else ->
                        CustomException.httpErrorWithObject( response)
                }
            }
            // A network error happened
            if (throwable is IOException) {
                return CustomException.networkError(throwable)
            }
            // We don't know what happened. We need to simply convert to an unknown error
            return CustomException.unexpectedError(throwable)
        }

    }
}