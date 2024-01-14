package com.example.wsaassignment.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.wsaassignment.dao.entities.FavoriteMovieData

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: FavoriteMovieData)

    @Update
    fun update(note: FavoriteMovieData)

    @Delete
    fun delete(note: FavoriteMovieData)

    @Query("delete from FavoriteTable")
    fun deleteAllFavorites()

    @Query("select * from FavoriteTable")
    fun getAllFavorites(): List<FavoriteMovieData>
}