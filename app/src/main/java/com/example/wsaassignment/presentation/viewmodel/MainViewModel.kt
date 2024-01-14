package com.example.wsaassignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wsaassignment.dao.entities.FavoriteMovieData
import com.example.wsaassignment.data.model.TrendingData
import com.example.wsaassignment.domain.usecase.FavoriteMovieUseCase
import com.example.wsaassignment.domain.usecase.TrendingMovieListUseCase
import com.example.wsaassignment.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val trendingMoviesUseCase: TrendingMovieListUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase) :
    ViewModel() {

    private val _trendingMoviesList = MutableStateFlow<TrendingData?>(null)
    val trendingMovieList
        get() = _trendingMoviesList

    private val _favoriteMoviesList = MutableStateFlow<List<FavoriteMovieData>>(emptyList())
    val favoriteMovieDataList
        get() = _favoriteMoviesList

    init {
        fetchMovies()
        fetchFavoritesMovieList()
    }

    fun lazyLoadElements() {
        val pageNumber = _trendingMoviesList.value?.page?.let { it + 1 } ?: 1
        fetchMovies(Constants.TimeWindow.WEEK.timeWindow, pageNumber = pageNumber)
    }

    fun fetchMovies(
        timeWindow: String = Constants.TimeWindow.WEEK.timeWindow,
        languageRange: String = Constants.DEFAULT_LANGUAGE,
        pageNumber: Int = 1
    ) {
        viewModelScope.launch {
            trendingMoviesUseCase<TrendingData>(
                scope = viewModelScope,
                TrendingMovieListUseCase.TrendingMovieDataMP.GetTrendingMovieList(
                    timeWindow,
                    languageRange,
                    pageNumber
                ),
                onSuccess = { trendingData ->
                    _trendingMoviesList.value?.let {
                        it.page = trendingData.page
                        it.totalPages = trendingData.totalPages
                        it.results.addAll(trendingData.results)
                        it.totalResults = trendingData.totalResults
                    }
                    if (_trendingMoviesList.value == null) {
                        _trendingMoviesList.value = trendingData
                    }
                },
                onError = null
            )
        }
    }

    fun fetchFavoritesMovieList() = viewModelScope.launch {
        favoriteMovieUseCase<List<FavoriteMovieData>>(viewModelScope,FavoriteMovieUseCase.GetFavoriteMoviesMP.GetFavoriteMovieList,
            onSuccess = {movieList -> _favoriteMoviesList.value = movieList},
            onError = null)
    }
}