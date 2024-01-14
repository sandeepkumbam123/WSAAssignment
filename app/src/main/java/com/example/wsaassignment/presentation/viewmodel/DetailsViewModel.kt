package com.example.wsaassignment.presentation.viewmodel

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wsaassignment.data.model.SeriesResult
import com.example.wsaassignment.data.model.TrendingData
import com.example.wsaassignment.domain.usecase.SeriesDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val detailsUseCase: SeriesDataUseCase
) : ViewModel() {

    private val _seriesResultValue = MutableStateFlow<SeriesResult?>(null)
    val seriesResultData
        get() = _seriesResultValue

    private val _similarSeriesData = MutableStateFlow<TrendingData?>(null)
    val similarSeriesInfo
        get() = _similarSeriesData

    companion object {
        const val keyTrendingSeriesData = "SeriesData"
    }

    init {
        loadDetailsScreenData()
    }


    private fun loadDetailsScreenData() {
        savedStateHandle.get<SeriesResult>(keyTrendingSeriesData)?.let {
            _seriesResultValue.value = it
            it.id?.let { seriesId -> fetchSimilarSeriesData(seriesId) }
        }
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
}