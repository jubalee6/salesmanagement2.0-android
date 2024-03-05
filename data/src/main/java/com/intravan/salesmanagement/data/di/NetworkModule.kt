package com.intravan.salesmanagement.data.di

import com.intravan.salesmanagement.core.BuildConfig
import com.intravan.salesmanagement.core.di.DefaultJson
import com.intravan.salesmanagement.core.util.DebugLog
import com.intravan.salesmanagement.core.util.UrlInterceptor
import com.intravan.salesmanagement.data.remote.api.IntravanApi
import com.intravan.salesmanagement.domain.datasource.local.PreferencesLocalDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.net.URLDecoder
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention
@Qualifier
annotation class IntravanOkHttpClient

@Retention
@Qualifier
annotation class IntravanRetrofit


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val INTERCEPTOR_LOGGING_NAME = "INTERCEPTOR_LOGGING"


    @Provides
    @Named(INTERCEPTOR_LOGGING_NAME)
    fun provideHttpLoggingInterceptor(): Interceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor { message ->
                if (message.indexOf("--> ") == 0 ||
                    message.indexOf("<-- ") == 0 ||
                    message.indexOf("Content-") == 0 ||
                    message.indexOf("params") == 0
                ) {
                    DebugLog.d { URLDecoder.decode(message, "UTF-8") }
                } else {
                    DebugLog.v { message }
                }
            }.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            noOpInterceptor()
        }
    }

    private fun noOpInterceptor(): Interceptor {
        return Interceptor { chain ->
            chain.proceed(chain.request())
        }
    }

    @IntravanOkHttpClient
    @Provides
    @Singleton
    fun provideIntravanOkHttpClient(
        @Named(INTERCEPTOR_LOGGING_NAME) loggingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .readTimeout(60000, TimeUnit.MILLISECONDS)
            .writeTimeout(10000, TimeUnit.MILLISECONDS)
            .apply {
                addInterceptor(UrlInterceptor)
                addNetworkInterceptor(loggingInterceptor)
            }
            .build()
    }

    @IntravanRetrofit
    @Provides
    @Singleton
    fun provideIntravanRetrofit(
        @DefaultJson json: Json,
        @IntravanOkHttpClient okHttpClient: OkHttpClient,
        preferences: PreferencesLocalDataSource
    ): Retrofit {
        val baseUrl = preferences.baseUrl
        val contentType = "application/json".toMediaType()
        return try {
            Retrofit
                .Builder()
                .addConverterFactory(json.asConverterFactory(contentType))
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
        } catch (ex: Exception) {
            // Debug.
            DebugLog.e { ">>>>> ${ex.stackTraceToString()}" }
            Retrofit
                .Builder()
                .addConverterFactory(json.asConverterFactory(contentType))
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .build()
        }
    }

    @Provides
    @Singleton
    fun provideIntravanApi(@IntravanRetrofit retrofit: Retrofit): IntravanApi {
        return retrofit.create(IntravanApi::class.java)
    }

}