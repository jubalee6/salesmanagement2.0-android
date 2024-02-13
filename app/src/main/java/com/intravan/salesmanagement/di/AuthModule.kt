package com.intravan.salesmanagement.di

import com.intravan.salesmanagement.presentation.ui.auth.AuthUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object AuthViewModelModule {

    @Provides
    fun provideInitialAuthUiState(): AuthUiState = AuthUiState()
}