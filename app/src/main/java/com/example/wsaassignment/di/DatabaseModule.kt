package com.example.wsaassignment.di

import android.content.Context
import com.example.wsaassignment.dao.FavoritesDao
import com.example.wsaassignment.database.FavoritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideFavoriteDatabase(@ApplicationContext context: Context) : FavoritesDatabase {
        FavoritesDatabase.initialize(context)
        return FavoritesDatabase.INSTANCE
    }

    @Provides
    fun providesFavoriteDao(appDatabase: FavoritesDatabase) : FavoritesDao = appDatabase.favoriteDao()
}