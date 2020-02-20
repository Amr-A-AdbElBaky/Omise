package com.omise.tamboon.core.di.module

import com.omise.tamboon.features.charities.di.CharityDataModule
import com.omise.tamboon.features.charities.di.CharityPresentationModule
import com.omise.tamboon.features.charities.presentation.view.CharitiesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [CharityDataModule::class, CharityPresentationModule::class])
    internal abstract fun charitiesActivity(): CharitiesActivity


}