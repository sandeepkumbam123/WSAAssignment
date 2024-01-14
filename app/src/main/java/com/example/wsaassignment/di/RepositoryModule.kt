package com.example.wsaassignment.di

import com.example.wsaassignment.data.repoimpl.FavoriteRepoImpl
import com.example.wsaassignment.data.repoimpl.MovieListRepoImpl
import com.example.wsaassignment.domain.repo.FavoriteMovieRepository
import com.example.wsaassignment.domain.repo.MovieListDbRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindMoviesListDB(repo : MovieListRepoImpl) : MovieListDbRepository

    @Singleton
    @Binds
    abstract fun binFavoritesDB(repo : FavoriteRepoImpl) : FavoriteMovieRepository
}