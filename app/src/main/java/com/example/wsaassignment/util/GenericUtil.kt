package com.example.wsaassignment.util

import com.example.wsaassignment.dao.entities.FavoriteMovieData
import com.example.wsaassignment.data.model.SeriesResult
import com.example.wsaassignment.data.model.TrendingData
import com.google.gson.Gson

inline fun <reified T> T.toJson(): String = Gson().toJson(this)

inline fun <reified T> String.fromJson(): T? {
    if (this.isNullOrEmpty().not()) {
        try {
            return Gson().fromJson(this, T::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return null
}


fun List<FavoriteMovieData>.toTrendingData(): TrendingData? {
    return if (this.isNullOrEmpty().not()) {
        TrendingData(
            page = 1,
            results = this.map { it.seriesData } as ArrayList<SeriesResult>,
            1,
            this.size)
    } else
        null
}