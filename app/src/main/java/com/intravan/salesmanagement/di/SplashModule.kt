package com.intravan.salesmanagement.di

import com.intravan.salesmanagement.presentation.ui.splash.SplashUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object SplashViewModelModule {

    @Provides
    fun provideInitialSplashUiState(): SplashUiState = SplashUiState()
}