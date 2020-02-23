package com.omise.tamboon.core.di.module


import android.content.Context
import com.google.gson.Gson
import com.omise.tamboon.BuildConfig.TAMBOON_BASE_URL
import com.omise.tamboon.core.application.TamboonApplication
import com.omise.tamboon.core.data.RxErrorHandlingCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: TamboonApplication): Context = application

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun getClient(context: Context , gson :Gson ): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()

            .client(okHttpClient)
            .baseUrl(TAMBOON_BASE_URL)
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }





}