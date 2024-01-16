package com.example.wsaassignment.domain.repo

import androidx.paging.PagingData
import com.example.wsaassignment.data.model.SeriesResult
import kotlinx.coroutines.flow.Flow

interface TrendingSeriesPageSource {
    suspend fun getTrendingData(pageNumber : Int) : Flow<PagingData<SeriesResult>>
}