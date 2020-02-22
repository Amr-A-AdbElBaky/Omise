package com.omise.tamboon.features.donations.di

import androidx.lifecycle.ViewModel
import com.omise.tamboon.core.di.ViewModelKey
import com.omise.tamboon.features.donations.data.repository.DonationRepositoryImp
import com.omise.tamboon.features.donations.data.source.DonationRemoteDS
import com.omise.tamboon.features.donations.data.source.api.DonationApis
import com.omise.tamboon.features.donations.domain.repository.DonationRepository
import com.omise.tamboon.features.donations.presentation.viewmodel.DonationViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
class DonationDataModule {
    @Provides
    fun provideDonationApis(retrofit: Retrofit)
            : DonationApis = retrofit.create(DonationApis::class.java)
    @Provides
    fun provideDonationRemoteDS(donationApis: DonationApis): DonationRemoteDS =
        DonationRemoteDS(donationApis)

    @Provides
    fun provideDonationRepositoryImp(donationRemoteDS: DonationRemoteDS): DonationRepository =
        DonationRepositoryImp(donationRemoteDS)

}
@Module
abstract class DonationPresentationModule {
    @Binds
    @IntoMap
    @ViewModelKey(DonationViewModel::class)
    internal abstract fun donationViewModel(viewModel: DonationViewModel): ViewModel

}

