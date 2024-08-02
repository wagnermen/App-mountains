package com.example.data.remote.di

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val token: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header("x-rapidapi-key", token)
            .build()
        return chain.proceed(modifiedRequest)
    }
}