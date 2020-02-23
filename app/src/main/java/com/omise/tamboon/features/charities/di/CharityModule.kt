package com.omise.tamboon.features.charities.di

import androidx.lifecycle.ViewModel
import com.omise.tamboon.core.di.ViewModelKey
import com.omise.tamboon.features.charities.data.repository.CharitiesRepositoryImp
import com.omise.tamboon.features.charities.data.source.remote.CharitiesRemoteDS
import com.omise.tamboon.features.charities.data.source.remote.network.CharitiesApis
import com.omise.tamboon.features.charities.domain.repository.CharitiesRepository
import com.omise.tamboon.features.charities.presentation.viewmodel.CharitiesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module
class CharityDataModule {
    @Provides
    fun provideCharitiesApis(retrofit: Retrofit)
            : CharitiesApis = retrofit.create(CharitiesApis::class.java)
    @Provides
    fun provideCharitiesRemoteDS(charitiesApis: CharitiesApis): CharitiesRemoteDS =
        CharitiesRemoteDS(charitiesApis)

    @Provides
    fun provideCharitiesRepositoryImp(charitiesRemoteDS: CharitiesRemoteDS): CharitiesRepository =
        CharitiesRepositoryImp(charitiesRemoteDS)

}
@Module
abstract class CharityPresentationModule {
    @Binds
    @IntoMap
    @ViewModelKey(CharitiesViewModel::class)
    internal abstract fun charitiesViewModel(viewModel: CharitiesViewModel): ViewModel

}

