package com.example.wsaassignment.domain.usecase

import com.example.wsaassignment.data.source.RemoteDataSource
import com.example.wsaassignment.di.Default
import com.example.wsaassignment.di.IO
import com.example.wsaassignment.domain.usecase.base.UseCase
import com.example.wsaassignment.util.Constants
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class TrendingMovieListUseCase @Inject constructor(private val dataSource: RemoteDataSource,
    @IO io : CoroutineDispatcher,
    @Default default : CoroutineDispatcher) : UseCase<TrendingMovieListUseCase.TrendingMovieDataMP>(default, io) {

    sealed interface TrendingMovieDataMP : MethodParam {
        data class GetTrendingMovieList(val timeWindow: Constants.TimeWindow,val language : String) : TrendingMovieDataMP
    }

    override suspend fun <Type> execute(mp: TrendingMovieDataMP): Type = when(mp) {
        is TrendingMovieDataMP.GetTrendingMovieList -> dataSource.getTrendingList(mp.timeWindow,mp.language)
    } as Type

}