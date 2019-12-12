package com.example.moviedetail.ui

import androidx.lifecycle.*
import com.example.moviedetail.domain.MovieDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tokopedia.app.abstraction.util.state.ResultState
import tokopedia.app.data.entity.Movie

class MovieDetailViewModel(private val useCase: MovieDetailUseCase): ViewModel(), MovieDetailContract {
    private val _movieId = MutableLiveData<String>()

    private val _movie = MediatorLiveData<Movie>()
    val movie : LiveData<Movie>
        get() = _movie

    private val _error = MutableLiveData<String>()
    val error : LiveData<String>
        get() = _error

    init {
        _movie.addSource(_movieId) {
            getMovieDetail(it)
        }
    }

    override fun setMovieId(movieId: String) {
        _movieId.value = movieId
    }

    override fun getMovieDetail(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase.get(movieId)
            withContext(Dispatchers.Main) {
                when (result) {
                    is ResultState.Success -> {
                        _movie.value = result.data
                    }
                    is ResultState.Error -> {
                        _error.value = result.error
                    }
                }
            }
        }
    }
}

interface MovieDetailContract {
    fun setMovieId(movieId: String)

    fun getMovieDetail(movieId: String)
}