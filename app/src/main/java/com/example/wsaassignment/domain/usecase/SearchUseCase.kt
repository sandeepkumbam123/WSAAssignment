package com.example.wsaassignment.domain.usecase

import com.example.wsaassignment.data.repoimpl.MovieListRepoImpl
import com.example.wsaassignment.di.Default
import com.example.wsaassignment.di.IO
import com.example.wsaassignment.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val movieListRepoImpl: MovieListRepoImpl,
    @Default default : CoroutineDispatcher,
    @IO io : CoroutineDispatcher )  : UseCase<SearchUseCase.GetSearchDataMP>(default, io) {

    sealed interface GetSearchDataMP : UseCase.MethodParam {
        data class GetSearchMovieListMP(val searchQuery : String) : GetSearchDataMP
    }

    override suspend fun <Type> execute(mp: GetSearchDataMP): Type = when(mp) {
        is GetSearchDataMP.GetSearchMovieListMP -> movieListRepoImpl.getSearchMovieList(searchQuery = mp.searchQuery)
    } as Type

}