package com.omise.tamboon.core.data

import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {

        val originalResponse = chain!!.proceed(chain.request())

        if (shouldLogout(originalResponse)) {
            // your logout logic here

            // send empty response down the chain
            return Response.Builder().build()

        }
        return originalResponse
    }

    private fun shouldLogout(response: Response) : Boolean {
        if (response.isSuccessful) {
            return false
        }

        // 401 and auth token means that we need to logout
        return (response.code() == 401 /*&&
                !response.headers().names().contains(AUTH_HEADER_KEY)*/)
    }
}