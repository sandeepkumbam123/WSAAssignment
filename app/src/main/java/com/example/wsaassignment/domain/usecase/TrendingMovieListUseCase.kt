package com.example.wsaassignment.domain.usecase

import com.example.wsaassignment.data.source.RemoteDataSource
import com.example.wsaassignment.di.Default
import com.example.wsaassignment.di.IO
import com.example.wsaassignment.domain.repo.TrendingSeriesPageSource
import com.example.wsaassignment.domain.usecase.base.UseCase
import com.example.wsaassignment.util.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class TrendingMovieListUseCase @Inject constructor(
    private val trendingDataSource: TrendingSeriesPageSource,
    @IO io: CoroutineDispatcher,
    @Default default: CoroutineDispatcher,
    private val rds : RemoteDataSource
) : UseCase<TrendingMovieListUseCase.TrendingMovieDataMP>(default, io) {

    sealed interface TrendingMovieDataMP : MethodParam {
        data class GetTrendingMovieList(
            val timeWindow: String,
            val language: String,
            val pageNumber: Int
        ) : TrendingMovieDataMP

        object GetTrendingMoviesDB : TrendingMovieDataMP
    }

    override suspend fun <Type> execute(mp: TrendingMovieDataMP): Type = when (mp) {
        is TrendingMovieDataMP.GetTrendingMovieList -> trendingDataSource.getTrendingData(pageNumber = mp.pageNumber)
        is TrendingMovieDataMP.GetTrendingMoviesDB -> rds.getTrendingList(pageNumber = 1, timeSeries = Constants.TimeWindow.WEEK.timeWindow)
    } as Type

}

