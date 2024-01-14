package com.example.wsaassignment.presentation.viewmodel

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wsaassignment.dao.FavoritesDao
import com.example.wsaassignment.dao.entities.FavoriteMovieData
import com.example.wsaassignment.data.model.SeriesResult
import com.example.wsaassignment.data.model.TrendingData
import com.example.wsaassignment.domain.usecase.FavoriteMovieUseCase
import com.example.wsaassignment.domain.usecase.SeriesDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val detailsUseCase: SeriesDataUseCase,
    val favoritesUseCase: FavoriteMovieUseCase,
    val favoritesDao: FavoritesDao
) : ViewModel() {

    val seriesResultData: SeriesResult?
        get() = savedStateHandle.get<SeriesResult>(keyTrendingSeriesData)

    private val _similarSeriesData = MutableStateFlow<TrendingData?>(null)
    val similarSeriesInfo
        get() = _similarSeriesData

    private val _favoriteMovieList = MutableStateFlow<List<FavoriteMovieData?>>(emptyList())
    val favoriteMovieList
        get() = _favoriteMovieList

    private val _isSeriesFavorite = MutableStateFlow(false)
    val isSeriesFavorited
        get() = _isSeriesFavorite

    companion object {
        const val keyTrendingSeriesData = "SeriesData"
    }

    init {
        favoriteMovies()
        loadDetailsScreenData()
    }


    private fun loadDetailsScreenData() {
        fetchSimilarSeriesData(seriesResultData?.id ?: 0)
    }

    private fun fetchSimilarSeriesData(seriesId: Int) {
        viewModelScope.launch {
            detailsUseCase<TrendingData>(
                scope = viewModelScope,
                SeriesDataUseCase.GetDetailsSeriesMP.GetSimilarSeriesMP(seriesId),
                onSuccess = { similarSeries -> _similarSeriesData.value = similarSeries },
                onError = null
            )
        }
    }

    fun fetchFavoriteMovies() = viewModelScope.launch {
        seriesResultData?.let { seriesResult ->
            favoritesUseCase<List<FavoriteMovieData?>>(
                scope = viewModelScope,
                FavoriteMovieUseCase.GetFavoriteMoviesMP.GetFavoriteMoviesCrudData(
                    FavoriteMovieData(seriesResult)
                ),
                onSuccess = {
                    _favoriteMovieList.value = it
                    _favoriteMovieList.value.let { seriesList ->
                        run {
                            val filterList =
                                seriesList.find { it?.seriesData?.id == seriesResultData?.id }
                            _isSeriesFavorite.value = filterList != null
                        }
                    }
                },
                onError = null
            )
        }
    }

    private fun favoriteMovies() = viewModelScope.launch {
        favoritesUseCase<List<FavoriteMovieData>>(
            viewModelScope,
            FavoriteMovieUseCase.GetFavoriteMoviesMP.GetFavoriteMovieList,
            onSuccess = {
                _favoriteMovieList.value = it
                _favoriteMovieList.value.let { seriesList ->
                    run {
                        val filterList =
                            seriesList.find {  it?.seriesData?.id == seriesResultData?.id }
                        _isSeriesFavorite.value = filterList != null
                    }
                }
            },
            onError = null
        )
    }
}