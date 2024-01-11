package com.example.wsaassignment.data.source

import com.example.wsaassignment.network.MovieApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor( movieApi: MovieApi) : MovieApi by movieApi