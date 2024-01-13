package com.example.wsaassignment.di

import android.support.multidex.BuildConfig
import com.example.wsaassignment.network.Interceptor.AuthInterceptor
import com.example.wsaassignment.network.MovieApi
import com.example.wsaassignment.util.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Generics
    fun provideGsonConverterFactory() : GsonConverterFactory = GsonConverterFactory.create(GsonBuilder().setLenient().create())
    @Provides
    @Singleton
    fun provideMovieAPI(
        @MovieDBUrl baseUrl: String,
        @Generics okHttpClient: OkHttpClient,
        @Generics gsonConverterFactory : GsonConverterFactory,
        @Generics rxJavaConverterFactory: RxJavaCallAdapterFactory) = createRetrofit(baseUrl,okHttpClient,gsonConverterFactory,rxJavaConverterFactory).create(MovieApi::class.java)

    @Provides
    @Generics
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, authInterceptor: AuthInterceptor) : OkHttpClient = createOkHttpClient(httpLoggingInterceptor, authInterceptor)

    @Provides
    @Generics
    fun providerxJavaCallAdapterFactory() = RxJavaCallAdapterFactory.create()


    private fun createOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, authInterceptor: AuthInterceptor) = OkHttpClient.Builder().apply {
        connectTimeout(Constants.CONNECTION_TIME_OUT,TimeUnit.SECONDS)
        connectTimeout(Constants.WRITE_TIME_OUT,TimeUnit.SECONDS)
        connectTimeout(Constants.READ_TIME_OUT,TimeUnit.SECONDS)
        addInterceptor(httpLoggingInterceptor)
        authInterceptor?.let {  addInterceptor(it)}
    }.build()

    private fun createRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        rxJavaConverterFactory: RxJavaCallAdapterFactory
    ): Retrofit = Retrofit.Builder().apply {
        baseUrl(baseUrl)
        addConverterFactory(gsonConverterFactory)
        addCallAdapterFactory(rxJavaConverterFactory)
        client(okHttpClient)
    }.build()

    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        setLevel(
           HttpLoggingInterceptor.Level.BODY
        )
    }

    @Provides
    fun provideAuthInterceptor() : AuthInterceptor = AuthInterceptor()

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