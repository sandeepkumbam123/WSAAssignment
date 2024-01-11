package com.example.wsaassignment.data.repoimpl

import com.example.wsaassignment.data.model.TrendingData
import com.example.wsaassignment.data.source.RemoteDataSource
import com.example.wsaassignment.domain.repo.MovieListDbRepository
import com.example.wsaassignment.util.Constants
import javax.inject.Inject

class MovieListRepoImpl @Inject constructor(val remoteDataSource: RemoteDataSource) : MovieListDbRepository {
    override suspend fun getTrendingMovieList(
        timeWindow: Constants.TimeWindow,
        language: String
    ): TrendingData = remoteDataSource.getTrendingList(timeWindow,language)

    override suspend fun getSearchMovieList(
        searchQuery: String,
        pageNumber: Int,
        language: String,
        year: String,
        firstAirDate: String,
        includeAdult: Boolean
    ): TrendingData = remoteDataSource.getSearchList(searchQuery,pageNumber,language,year,includeAdult,firstAirDate)

}