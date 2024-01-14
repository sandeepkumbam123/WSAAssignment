package com.example.wsaassignment.domain.repo

import com.example.wsaassignment.data.model.TrendingData
import com.example.wsaassignment.util.Constants

interface MovieListDbRepository {

    suspend fun getTrendingMovieList(timeWindow: String, language: String, pageNumber: Int) : TrendingData

    suspend fun getSearchMovieList(
        searchQuery: String,
        pageNumber: Int,
        language: String,
        year: String,
        firstAirDate: String,
        includeAdult: Boolean
    ) : TrendingData

    suspend fun getSimilarShows( seriesId : Int) : TrendingData
}