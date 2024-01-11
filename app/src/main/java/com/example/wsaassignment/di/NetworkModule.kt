package com.example.wsaassignment.di

import com.example.wsaassignment.network.MovieApi
import com.example.wsaassignment.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Provides
    @Generics
    fun provideGsonConverterFactory() : GsonConverterFactory = GsonConverterFactory.create()
    @Provides
    @Singleton
    fun provideMovieAPI(
        @MovieDBUrl baseUrl: String,
        @Generics okHttpClient: OkHttpClient,
        @Generics gsonConverterFactory : GsonConverterFactory) = createRetrofit(baseUrl,okHttpClient,gsonConverterFactory).create(MovieApi::class.java)

    @Provides
    @Generics
    fun provideOkHttpClient() : OkHttpClient = createOkHttpClient()


    private fun createOkHttpClient() = OkHttpClient.Builder().apply {
        connectTimeout(Constants.CONNECTION_TIME_OUT,TimeUnit.SECONDS)
        connectTimeout(Constants.WRITE_TIME_OUT,TimeUnit.SECONDS)
        connectTimeout(Constants.READ_TIME_OUT,TimeUnit.SECONDS)
    }.build()

    private fun createRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder().apply {
        baseUrl(baseUrl)
        addConverterFactory(gsonConverterFactory)
        client(okHttpClient)
    }.build()

    @Provides
    @MovieDBUrl
    fun provideBaseUrl() = Constants.BASE_URL

    @Qualifier
    @MustBeDocumented
    @Retention(AnnotationRetention.RUNTIME)
    annotation class MovieDBUrl

    @Qualifier
    @MustBeDocumented
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Generics
}