package com.intravan.salesmanagement.di

import com.intravan.salesmanagement.presentation.ui.company.CompanyUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CompanyViewModelModule {

    @Provides
    fun provideInitialCompanyUiState(): CompanyUiState = CompanyUiState()
}