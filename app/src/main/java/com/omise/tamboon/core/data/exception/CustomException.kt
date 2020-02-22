package com.omise.tamboon.core.data.exception

import retrofit2.Response
import java.io.IOException


class CustomException(override val message: String?, val kind: Kind) : RuntimeException(message) {

    companion object {
        // this will return error message but when the error model is knoen
        fun httpErrorWithObject(response: Response<*>?): CustomException {
            var errorMessage: String? = null
            var responseString = ""
            var statusCode = 0
            if (response != null) {
                statusCode = response.code()
            }
            try {
                if (response?.errorBody() != null) {
                    responseString = response.errorBody()!!.string()
                }


            } catch (e: Exception) {
                if (response != null)
                    errorMessage = statusCode.toString() + " " + response.message()
                e.printStackTrace()
            }

            return CustomException(errorMessage, kind = Kind.HTTP)
        }

        fun userNotVerified(response: Response<*>): CustomException {
            val message = response.code().toString() + " " + response.message()
            return CustomException(message = message, kind = Kind.USER_NOT_VERIFIED)
        }

        fun notAuthorized(response: Response<*>): CustomException {
            return CustomException("", Kind.NOT_AUTHORIZED)
        }

        fun httpError(response: Response<*>): CustomException {
            return CustomException(response.errorBody().toString(), Kind.HTTP)
        }

        fun networkError(exception: IOException): CustomException {
            return CustomException(exception.message, Kind.NETWORK)
        }

        fun unexpectedError(exception: Throwable): CustomException {
            return CustomException(exception.message, Kind.UNEXPECTED)
        }
    }


    enum class Kind {
        NETWORK,
        HTTP,
        USER_NOT_VERIFIED,
        NOT_AUTHORIZED,
        UNEXPECTED
    }
}