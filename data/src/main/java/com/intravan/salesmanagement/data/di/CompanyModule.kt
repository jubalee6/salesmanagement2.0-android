package com.intravan.salesmanagement.data.di

import com.intravan.salesmanagement.data.datasource.local.CompanyLocalDataSourceImpl
import com.intravan.salesmanagement.data.datasource.remote.CompanyRemoteDataSourceImpl
import com.intravan.salesmanagement.data.repository.CompanyRepositoryImpl
import com.intravan.salesmanagement.domain.datasource.local.CompanyLocalDataSource
import com.intravan.salesmanagement.domain.datasource.remote.CompanyRemoteDataSource
import com.intravan.salesmanagement.domain.repository.CompanyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [CompanyModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
object CompanyModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {
        @Binds
        @Singleton
        fun bindCompanyRepository(repositoryImpl: CompanyRepositoryImpl): CompanyRepository

        @Binds
        @Singleton
        fun bindCompanyRemoteDataSource(remoteDataSourceImpl: CompanyRemoteDataSourceImpl): CompanyRemoteDataSource

        @Binds
        @Singleton
        fun bindCompanyLocalDataSource(localDataSourceImpl: CompanyLocalDataSourceImpl): CompanyLocalDataSource
    }
}