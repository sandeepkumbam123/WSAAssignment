package com.example.wsaassignment.domain.repo

import com.example.wsaassignment.dao.entities.FavoriteMovieData

interface FavoriteMovieRepository {
    suspend fun getFavoriteMovies(): List<FavoriteMovieData>

    suspend fun deleteFavoriteMovie(favoriteMovie : FavoriteMovieData)

    suspend fun insertFavoriteMovie(movieData: FavoriteMovieData)
}