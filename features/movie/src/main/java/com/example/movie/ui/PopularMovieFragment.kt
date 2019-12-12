package com.example.movie.ui

import android.util.Log
import android.widget.GridLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie.R
import com.example.movie.domain.PopularMovieUseCase
import com.example.movie.factory.PopularMovieFactory
import kotlinx.android.synthetic.main.fragment_popular_movie.*
import tokopedia.app.abstraction.base.BaseFragment
import tokopedia.app.data.entity.Movie
import tokopedia.app.data.repository.movie.MovieRepository
import tokopedia.app.data.repository.movie.MovieRepositoryImpl
import tokopedia.app.data.routes.NetworkServices
import tokopedia.app.network.Network

class PopularMovieFragment: BaseFragment() {

    private lateinit var repository: MovieRepository
    private lateinit var useCase: PopularMovieUseCase
    private lateinit var viewModel: PopularMovieViewModel

    //adapter
    private val movies = mutableListOf<Movie>()
    private val adapter by lazy {
        PopularMovieAdapter(movies)
    }

    override fun contentView(): Int {
        return R.layout.fragment_popular_movie
    }

    override fun initObservable() {
        val networkBuilder = Network.builder().create(NetworkServices::class.java)

        //init repository
        repository = MovieRepositoryImpl(networkBuilder)

        //init useCase
        useCase = PopularMovieUseCase(repository)

        //init viewmodel
        viewModel = ViewModelProviders
            .of(this, PopularMovieFactory(useCase))
            .get(PopularMovieViewModel::class.java)
    }

    override fun initView() {
        recycler_view?.layoutManager = GridLayoutManager(context, 2)
        recycler_view?.adapter = adapter

        viewModel.error.observe(viewLifecycleOwner, showError())

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            movies.clear()
            movies.addAll(it.resultsIntent)
            adapter.notifyDataSetChanged()
        })
    }

    private fun showError(): Observer<String> {
        return Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
}