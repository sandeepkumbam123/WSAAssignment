package com.example.wsaassignment.network

import com.example.wsaassignment.data.model.TrendingData
import com.example.wsaassignment.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET(Constants.API_ENDPOINT_TRENDING)
    suspend fun getTrendingList(
        @Path(Constants.PATH_TIME_WINDOW) timeSeries: String,
        @Query(Constants.QUERY_PARAM_LANGUAGE) language: String = Constants.DEFAULT_LANGUAGE,
        @Query(Constants.QUERY_PARAM_PAGE) pageNumber: Int = 1,
        @Query(Constants.QUERY_PARAM_API_KEY) apiKey: String = Constants.API_KEY
    ): TrendingData

    @GET(Constants.API_ENDPOINT_SEARCH)
    suspend fun getSearchList(
        @Query(Constants.QUERY_PARAM_SEARCH_QUERY) queryString: String,
        @Query(Constants.QUERY_PARAM_PAGE) pageNumber: Int = 1,
        @Query(Constants.QUERY_PARAM_LANGUAGE) language: String = Constants.DEFAULT_LANGUAGE,
        @Query(Constants.QUERY_PARAM_YEAR) year: String = Constants.BLANK,
        @Query(Constants.QUERY_PARAM_INCLUDE_ADULT) includeAdult: Boolean = false,
        @Query(Constants.QUERY_PARAM_FIRST_AIR_DATE) firstAirDate: String = Constants.BLANK,
        @Query(Constants.QUERY_PARAM_API_KEY) apiKey: String = Constants.API_KEY
    ): TrendingData

    @GET(Constants.API_ENDPOINT_SIMILAR_SHOWS)
    suspend fun getSimilarShows(
        @Path(Constants.QUERY_PARAM_SERIES_ID) seriesId: Int,
        @Query(Constants.QUERY_PARAM_PAGE) pageNumber: Int = 1,
        @Query(Constants.QUERY_PARAM_LANGUAGE) language: String = Constants.DEFAULT_LANGUAGE,
        @Query(Constants.QUERY_PARAM_API_KEY) apiKey: String = Constants.API_KEY
    ) : TrendingData

}