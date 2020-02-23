package com.omise.tamboon.features.splash.di

import androidx.lifecycle.ViewModel
import com.omise.tamboon.core.di.ViewModelKey
import com.omise.tamboon.features.splash.presentation.viewmodel.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SplashPresentationModule {
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun splashViewModel(viewModel: SplashViewModel): ViewModel

}

