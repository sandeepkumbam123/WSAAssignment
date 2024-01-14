package com.example.wsaassignment.domain.usecase

import com.example.wsaassignment.dao.FavoritesDao
import com.example.wsaassignment.dao.entities.FavoriteMovieData
import com.example.wsaassignment.di.Default
import com.example.wsaassignment.di.IO
import com.example.wsaassignment.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FavoriteMovieUseCase @Inject constructor(
    val favoritesDao: FavoritesDao,
    @Default default: CoroutineDispatcher,
    @IO io: CoroutineDispatcher
) : UseCase<FavoriteMovieUseCase.GetFavoriteMoviesMP>(default, io) {

    sealed interface GetFavoriteMoviesMP : MethodParam {
        data class GetFavoriteMoviesCrudData(val movie: FavoriteMovieData) :
            GetFavoriteMoviesMP

        object GetFavoriteMovieList : GetFavoriteMoviesMP
    }

    override suspend fun <Type> execute(mp: GetFavoriteMoviesMP): Type = when (mp) {
        is GetFavoriteMoviesMP.GetFavoriteMoviesCrudData -> fetchMoviesData(mp.movie)
        is GetFavoriteMoviesMP.GetFavoriteMovieList -> favoritesDao.getAllFavorites()
    } as Type

    suspend fun fetchMoviesData(
        movie: FavoriteMovieData
    ): List<FavoriteMovieData> {

        val favoriteMoves = favoritesDao.getAllFavorites()
        val movieExist = favoriteMoves.find { it.seriesData.id == movie.seriesData.id }
        if (movieExist != null) {
            favoritesDao.deleteById(movie.tableId ?: movie.seriesData.id ?: 0)
        } else {
            favoritesDao.insert(movie)
        }
        return favoritesDao.getAllFavorites()
    }
}