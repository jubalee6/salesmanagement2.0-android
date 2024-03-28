package com.intravan.salesmanagement.data.di

import com.intravan.salesmanagement.data.datasource.local.SplashLocalDataSourceImpl
import com.intravan.salesmanagement.data.datasource.remote.SplashRemoteDataSourceImpl
import com.intravan.salesmanagement.data.repository.SplashRepositoryImpl
import com.intravan.salesmanagement.domain.datasource.local.SplashLocalDataSource
import com.intravan.salesmanagement.domain.datasource.remote.SplashRemoteDataSource
import com.intravan.salesmanagement.domain.repository.SplashRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [SplashModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
object SplashModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindSplashRepository(repositoryImpl: SplashRepositoryImpl): SplashRepository

        @Binds
        @Singleton
        fun bindSplashLocalDataSource(dataSourceImpl: SplashLocalDataSourceImpl): SplashLocalDataSource

        @Binds
        @Singleton
        fun bindSlashRemoteDataSource(dataSourceImpl: SplashRemoteDataSourceImpl): SplashRemoteDataSource
    }
}