package com.omise.tamboon.core.di.module

import com.omise.tamboon.features.charities.di.CharityDataModule
import com.omise.tamboon.features.charities.di.CharityPresentationModule
import com.omise.tamboon.features.charities.presentation.view.CharitiesActivity
import com.omise.tamboon.features.donations.di.DonationDataModule
import com.omise.tamboon.features.donations.di.DonationPresentationModule
import com.omise.tamboon.features.donations.presentation.view.DonationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [CharityDataModule::class, CharityPresentationModule::class])
    internal abstract fun charitiesActivity(): CharitiesActivity

    @ContributesAndroidInjector(modules = [DonationDataModule::class, DonationPresentationModule::class])
    internal abstract fun donationActivity(): DonationActivity

}