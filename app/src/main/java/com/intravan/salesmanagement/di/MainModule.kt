package com.intravan.salesmanagement.di

import com.intravan.salesmanagement.presentation.ui.main.MainUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object MainViewModelModule {

    @Provides
    fun provideInitialMainUiState():MainUiState = MainUiState()
}