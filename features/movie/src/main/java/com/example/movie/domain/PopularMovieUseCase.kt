package com.example.movie.domain

import tokopedia.app.abstraction.util.state.ResultState
import tokopedia.app.data.entity.Movies
import tokopedia.app.data.repository.movie.MovieRepository
import tokopedia.app.data.repository.moviedetail.MovieDetailRepository

class PopularMovieUseCase constructor(
    private val repository: MovieRepository
) {
    suspend fun get(): ResultState<Movies> {
        val popularMovie = repository.getPopularMovie()
        return if (popularMovie.isSuccessful) {
            ResultState.Success(popularMovie.body()!!)
        } else {
            ResultState.Error(MOVIE_ERROR)
        }
    }

    companion object {
        private const val MOVIE_ERROR = "Error get movie data!"
    }
}