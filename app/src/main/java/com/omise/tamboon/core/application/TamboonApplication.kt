package com.omise.tamboon.core.application

import com.omise.tamboon.core.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class TamboonApplication :  DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun applicationInjector(): AndroidInjector<TamboonApplication> =
        DaggerAppComponent.builder().create(this)

    companion object{
        var instance  :TamboonApplication? = null
    }

}