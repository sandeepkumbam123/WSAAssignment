package com.example.wsaassignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wsaassignment.data.model.TrendingData
import com.example.wsaassignment.domain.usecase.TrendingMovieListUseCase
import com.example.wsaassignment.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val trendingMoviesUseCase : TrendingMovieListUseCase) : ViewModel() {

    private val _trendingMoviesList = MutableStateFlow<TrendingData?>(null)
    val trendingMovieList
        get() = _trendingMoviesList
    init {
        fetchMovies()
    }

    private fun fetchMovies(timeWindow: Constants.TimeWindow = Constants.TimeWindow.DAY,languageRange: String = Constants.DEFAULT_LANGUAGE) {
        viewModelScope.launch { trendingMoviesUseCase<TrendingData>(scope = viewModelScope,TrendingMovieListUseCase.TrendingMovieDataMP.GetTrendingMovieList(timeWindow,languageRange),
            onSuccess = {_trendingMoviesList.value = it},
            onError = null) }
    }
}