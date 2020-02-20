package com.omise.tamboon.core.di.component


import com.omise.tamboon.core.application.TamboonApplication
import com.omise.tamboon.core.di.module.ActivityBuilder
import com.omise.tamboon.core.di.module.AppModule
import com.omise.tamboon.core.di.module.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ViewModelModule::class, ActivityBuilder::class])
interface AppComponent : AndroidInjector<TamboonApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TamboonApplication>()

}
