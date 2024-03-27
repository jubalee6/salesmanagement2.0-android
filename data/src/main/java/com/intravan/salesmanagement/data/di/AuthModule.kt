package com.intravan.salesmanagement.data.di

import com.intravan.salesmanagement.data.datasource.local.AuthLocalDataSourceImpl
import com.intravan.salesmanagement.data.datasource.remote.AuthRemoteDataSourceImpl
import com.intravan.salesmanagement.data.repository.AuthRepositoryImpl
import com.intravan.salesmanagement.domain.datasource.local.AuthLocalDataSource
import com.intravan.salesmanagement.domain.datasource.remote.AuthRemoteDataSource
import com.intravan.salesmanagement.domain.repository.AuthRepository
import com.intravan.salesmanagement.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [AuthModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {


        @Binds
        @Singleton
        fun bindAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository

        @Binds
        @Singleton
        fun bindAuthLocalDataSource(dataSourceImpl: AuthLocalDataSourceImpl) : AuthLocalDataSource


        @Binds
        @Singleton
        fun bindAuthRemoteDataSource(dataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource
    }
}