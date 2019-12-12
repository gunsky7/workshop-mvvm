package com.example.moviedetail.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.moviedetail.domain.MovieDetailUseCase
import com.example.moviedetail.ui.MovieDetailViewModel

class MovieDetailFactory(private val useCase: MovieDetailUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(useCase) as T
    }
}