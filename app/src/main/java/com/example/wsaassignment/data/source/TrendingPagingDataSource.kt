package com.example.wsaassignment.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wsaassignment.data.model.SeriesResult
import com.example.wsaassignment.util.Constants
import javax.inject.Inject

class TrendingPagingDataSource @Inject constructor(val remoteDataSource: RemoteDataSource) : PagingSource<Int,SeriesResult>() {
    override fun getRefreshKey(state: PagingState<Int, SeriesResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SeriesResult> {
        return try {
            val page = params.key ?: 1
            val response = remoteDataSource.getTrendingList(pageNumber = page, timeSeries = Constants.TimeWindow.WEEK.timeWindow)

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}