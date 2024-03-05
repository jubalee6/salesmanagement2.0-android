package com.intravan.salesmanagement.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention
@Qualifier
annotation class DefaultJson // UI-related

@Retention
@Qualifier
annotation class PrettyPrintJson // UI-related

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @DefaultJson
    @Provides
    @Singleton
    fun provideDefaultJson(): Json {
        return Json {
            isLenient = true
            prettyPrint = false
            encodeDefaults = true
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
    }

    @PrettyPrintJson
    @Provides
    @Singleton
    fun providePrettyPrintJson(): Json {
        return Json {
            isLenient = true
            prettyPrint = true
            encodeDefaults = true
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
    }
}
