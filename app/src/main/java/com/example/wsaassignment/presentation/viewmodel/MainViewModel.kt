package com.example.wsaassignment.presentation.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wsaassignment.dao.entities.FavoriteMovieData
import com.example.wsaassignment.data.model.SeriesResult
import com.example.wsaassignment.data.model.TrendingData
import com.example.wsaassignment.domain.usecase.FavoriteMovieUseCase
import com.example.wsaassignment.domain.usecase.SearchUseCase
import com.example.wsaassignment.domain.usecase.TrendingMovieListUseCase
import com.example.wsaassignment.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val trendingMoviesUseCase: TrendingMovieListUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase,
    private val searchUseCase: SearchUseCase
) :
    ViewModel() {

    private val _trendingMoviesList: MutableStateFlow<PagingData<SeriesResult>> =
        MutableStateFlow(value = PagingData.empty())
    val trendingMovieList
        get() = _trendingMoviesList
    private val _searchMovieList = MutableStateFlow<List<SeriesResult>>(emptyList())
    val serchMovieResult
        get() = _searchMovieList

    private val _favoriteMoviesList = MutableStateFlow<List<FavoriteMovieData>>(emptyList())
    val favoriteMovieDataList
        get() = _favoriteMoviesList

    //first state whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching

    //second state the text typed by the user
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText

    private var _error = MutableStateFlow(Constants.BLANK)
    val error
        get() = _error

    init {
        fetchMovies()
        fetchFavoritesMovieList()
    }

    fun fetchMovies(
        timeWindow: String = Constants.TimeWindow.WEEK.timeWindow,
        languageRange: String = Constants.DEFAULT_LANGUAGE,
        pageNumber: Int = 1
    ) {
        viewModelScope.launch {

        }
        viewModelScope.launch {
            trendingMoviesUseCase<Flow<PagingData<SeriesResult>>>(
                scope = viewModelScope,
                TrendingMovieListUseCase.TrendingMovieDataMP.GetTrendingMovieList(
                    timeWindow,
                    languageRange,
                    pageNumber
                ),
                onSuccess = { trendingData ->
                    trendingData.cachedIn(this)
                    _trendingMoviesList.cachedIn(this)
                    viewModelScope.launch {
                        trendingData.collect { _trendingMoviesList.value = it }
                    }
                },
                onError = { throwable, response ->
                    run {
                        _error.value = ERROR_HANDLING.API_ERROR.errorType
                    }
                }
            )
        }
    }

    fun fetchFavoritesMovieList() = viewModelScope.launch {
        favoriteMovieUseCase<List<FavoriteMovieData>>(
            viewModelScope, FavoriteMovieUseCase.GetFavoriteMoviesMP.GetFavoriteMovieList,
            onSuccess = { movieList -> _favoriteMoviesList.value = movieList },
            onError = null
        )
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        fetchSearchItems(text)
    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        val connected = connectivityManager!!.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!
            .state == NetworkInfo.State.CONNECTED ||
                connectivityManager!!.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED
        return connected
    }

//    fun updateTrendingDatainNoNetwork() {
//        if (_favoriteMoviesList.value.isNullOrEmpty().not()) {
//            _trendingMoviesList.value = _favoriteMoviesList.value.toTrendingData()
//        }
//    }

    private fun fetchSearchItems(query: String) {
        if (query.isNotEmpty() && query.length >= 2) {
            viewModelScope.launch {
                searchUseCase<TrendingData>(
                    scope = viewModelScope,
                    SearchUseCase.GetSearchDataMP.GetSearchMovieListMP(query),
                    onSuccess = { searchMovieList ->
                        _searchMovieList.value = searchMovieList.results
                    },
                    onError = null
                )
            }
        } else {
            viewModelScope.launch {
                trendingMoviesUseCase<TrendingData>(
                    viewModelScope,
                    TrendingMovieListUseCase.TrendingMovieDataMP.GetTrendingMoviesDB,
                    onSuccess = { movieList -> _searchMovieList.value = movieList.results },
                    onError = null
                )
            }
        }
    }

    enum class ERROR_HANDLING(var errorType: String) {
        API_ERROR("Something went Wrong")
    }
}

