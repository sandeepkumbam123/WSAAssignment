package com.example.wsaassignment.data.repoimpl

import com.example.wsaassignment.dao.FavoritesDao
import com.example.wsaassignment.dao.entities.FavoriteMovieData
import com.example.wsaassignment.domain.repo.FavoriteMovieRepository
import javax.inject.Inject

class FavoriteRepoImpl @Inject constructor() : FavoriteMovieRepository{
    override suspend fun getFavoriteMovies(): List<FavoriteMovieData> = emptyList()
    override suspend fun deleteFavoriteMovie(movieData: FavoriteMovieData) {
//        favoriteDao.delete(movieData)
    }

    override suspend fun insertFavoriteMovie(movieData: FavoriteMovieData) {
//       favoriteDao.insert(movieData)
    }

}