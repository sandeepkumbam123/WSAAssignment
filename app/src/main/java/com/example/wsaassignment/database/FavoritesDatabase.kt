package com.example.wsaassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.wsaassignment.dao.FavoritesDao
import com.example.wsaassignment.dao.entities.FavoriteMovieData
import com.example.wsaassignment.util.SeriesConverters
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [FavoriteMovieData::class], version = 1, exportSchema = false)
@TypeConverters(SeriesConverters::class)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun favoriteDao() : FavoritesDao

    companion object {
        val DB_NAME = "FavoriteMovies"
       lateinit var INSTANCE: FavoritesDatabase
       private set

       @Synchronized
        fun initialize(@ApplicationContext context: Context) {
            if (::INSTANCE.isInitialized.not()) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    FavoritesDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
        }

        @Synchronized
        fun initandGetAppDatabase(@ApplicationContext applicationContext: Context) : FavoritesDatabase {
            initialize(applicationContext)
            return INSTANCE
        }
    }
}