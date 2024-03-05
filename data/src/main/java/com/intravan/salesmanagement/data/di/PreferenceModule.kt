package com.intravan.salesmanagement.data.di

import com.intravan.salesmanagement.data.datasource.local.PreferencesLocalDataSourceImpl
import com.intravan.salesmanagement.domain.datasource.local.PreferencesLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [PreferenceModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule{

        @Binds
        @Singleton
        fun bindPreferencesLocalDataSource(dataSourceImpl: PreferencesLocalDataSourceImpl) : PreferencesLocalDataSource
    }
}