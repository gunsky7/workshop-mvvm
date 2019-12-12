package com.example.moviedetail.ui

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.moviedetail.R
import com.example.moviedetail.domain.MovieDetailUseCase
import com.example.moviedetail.factory.MovieDetailFactory
import tokopedia.app.abstraction.base.BaseActivity
import tokopedia.app.data.repository.moviedetail.MovieDetailRepository
import tokopedia.app.data.repository.moviedetail.MovieDetailRepositoryImpl
import tokopedia.app.data.routes.NetworkServices
import tokopedia.app.network.Network

class MovieDetailActivity: BaseActivity() {

    private lateinit var repository: MovieDetailRepository
    private lateinit var useCase: MovieDetailUseCase
    private lateinit var viewModel: MovieDetailViewModel


    override fun contentView(): Int {
        return R.layout.activity_movie_detail
    }

    override fun initView() {
        intent?.data?.lastPathSegment?.let {
            viewModel.setMovieId(it)
        }

        viewModel.error.observe(this, onShowError())

        viewModel.movie.observe(this, Observer {
            showToast(it.title)
        })
    }

    override fun initObservable() {
        val networkBuilder = Network.builder().create(NetworkServices::class.java)

        //init repository
        repository = MovieDetailRepositoryImpl(networkBuilder)

        //init useCase
        useCase = MovieDetailUseCase(repository)

        //init viewmodel
        viewModel = ViewModelProviders
            .of(this, MovieDetailFactory(useCase))
            .get(MovieDetailViewModel::class.java)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun onShowError() : Observer<String> {
        return Observer { showToast(it) }
    }

}