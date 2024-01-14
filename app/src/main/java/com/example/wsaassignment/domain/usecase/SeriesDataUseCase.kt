package com.example.wsaassignment.domain.usecase

import com.example.wsaassignment.data.model.SeriesResult
import com.example.wsaassignment.data.model.TrendingData
import com.example.wsaassignment.data.source.RemoteDataSource
import com.example.wsaassignment.di.Default
import com.example.wsaassignment.di.IO
import com.example.wsaassignment.domain.repo.MovieListDbRepository
import com.example.wsaassignment.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SeriesDataUseCase @Inject constructor(@IO io : CoroutineDispatcher,
                                            @Default default : CoroutineDispatcher,
                                            private val repo: MovieListDbRepository) : UseCase<SeriesDataUseCase.GetDetailsSeriesMP>(default,io){

        sealed interface GetDetailsSeriesMP : MethodParam {
            data class GetSimilarSeriesMP(val seriesId : Int) : GetDetailsSeriesMP
        }

    override suspend fun <Type> execute(mp: GetDetailsSeriesMP): Type = when(mp) {
       is GetDetailsSeriesMP.GetSimilarSeriesMP -> {
           fetchSimilarSeries(mp.seriesId)
       }
    } as Type

    private suspend fun fetchSimilarSeries(seriesId : Int) : TrendingData  = repo.getSimilarShows(seriesId)


}