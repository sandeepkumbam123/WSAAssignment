package com.example.wsaassignment.util

import androidx.room.TypeConverter
import com.example.wsaassignment.data.model.SeriesResult
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class SeriesConverters {
    @TypeConverter
    fun toSeriesDataString(seriesData: SeriesResult): String = GsonBuilder().create().toJson(seriesData)

    @TypeConverter
    fun toSeriesData(seriesString:  String): SeriesResult {
        val listType = object : TypeToken<SeriesResult>() {}.type
        return GsonBuilder().create().fromJson(seriesString, listType)
    }
}