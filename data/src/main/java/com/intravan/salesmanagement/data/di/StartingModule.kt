package com.intravan.salesmanagement.data.di

import com.intravan.salesmanagement.data.repository.StartingRepositoryImpl
import com.intravan.salesmanagement.domain.repository.StartingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [StartingModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
object StartingModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindStartingRepository(repositoryImpl: StartingRepositoryImpl): StartingRepository
    }
}