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
    suspend fun update(note: FavoriteMovieData)

    @Delete
    suspend fun delete(note: FavoriteMovieData)

    @Query("DELETE FROM FavoriteTable WHERE tableId = :id")
    suspend fun deleteById(id : Int)

    @Query("select * from FavoriteTable")
    fun getAllFavorites(): List<FavoriteMovieData>
}