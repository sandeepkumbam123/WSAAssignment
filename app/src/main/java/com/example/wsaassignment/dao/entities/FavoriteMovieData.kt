package com.example.wsaassignment.dao.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.wsaassignment.data.model.SeriesResult
import com.example.wsaassignment.util.SeriesConverters

@Entity(tableName = "FavoriteTable")
class FavoriteMovieData(
    @TypeConverters(SeriesConverters::class)
    @ColumnInfo(name = "seriesResult") val seriesData: SeriesResult
) {
    @PrimaryKey(autoGenerate = false) var tableId = seriesData.id
}