package com.example.wsaassignment.data.repoimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.wsaassignment.data.source.RemoteDataSource
import com.example.wsaassignment.data.source.TrendingPagingDataSource
import com.example.wsaassignment.domain.repo.TrendingSeriesPageSource
import javax.inject.Inject

class TrendingPageSourceImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) : TrendingSeriesPageSource {
    override suspend fun getTrendingData(pageNumber: Int) = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            TrendingPagingDataSource(remoteDataSource = remoteDataSource)
        }
    ).flow
}