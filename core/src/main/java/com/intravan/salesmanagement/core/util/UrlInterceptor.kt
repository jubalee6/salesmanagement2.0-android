package com.intravan.salesmanagement.core.util

import com.intravan.salesmanagement.core.BuildConfig
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * An interceptor that allows runtime changes to the API Base URL in Retrofit.
 * The Base URL is set by calling the [UrlInterceptor.baseUrl] method.
 */
object UrlInterceptor : Interceptor {

    // 초기 URL.
    var baseHttpUrl = BuildConfig.BASE_URL.toHttpUrlOrNull()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        // Chain.
        var request = chain.request()

        // 변경 된 host 적용.
        if (baseHttpUrl?.host != request.url.host) {
            baseHttpUrl?.let { baseHttpUrl ->
                request = request
                    .url
                    .newBuilder()
                    .apply {
                        scheme(baseHttpUrl.scheme)
                        host(baseHttpUrl.host)
                    }.let {
                        request
                            .newBuilder()
                            .url(it.build())
                            .build()
                    }
            }

            // Debug.
            DebugLog.i { ">>>>> [BASE_URL] IVBaseUrlInterceptor : $baseHttpUrl" }
            DebugLog.i { ">>>>> [B~] IVBaseUrlInterceptor : ${chain.request()}" }
            DebugLog.i { ">>>>> [A~] IVBaseUrlInterceptor : $request" }
        }

        return chain.proceed(request)
    }
}
