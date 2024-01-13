package com.example.wsaassignment.network.Interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder : Request.Builder = original.newBuilder()
        requestBuilder.addHeader("appPlatform","android")
        requestBuilder.addHeader("accept","application/json")
        requestBuilder.addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3OGQ3OGFkZTBlN2FmZTc2ZjAyMjM2NDc5OWFjMGJlMyIsInN1YiI6IjY1YTAxN2Q2YmVmYjA5MDEyOTg0NmM4OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.SJC8J-lIn-pe4OhdsTBo4Bhy_wMCCn1YyKnvzb6EyNM")
        requestBuilder.method(original.method,original.body)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}