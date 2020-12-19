package com.alie.submission.api

import com.alie.submission.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MyInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request : Request = chain.request()
            .newBuilder()
            .addHeader("User-Agent","request")
            .addHeader("Authorization","token ${BuildConfig.GITHUB_TOKEN}")
            .build()
        return chain.proceed(request)
    }

}