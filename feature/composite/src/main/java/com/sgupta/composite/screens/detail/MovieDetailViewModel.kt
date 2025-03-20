package com.sgupta.composite.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgupta.core.network.Resource
import com.sgupta.domain.usecase.GetMoviesDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMoviesDetailUseCase: GetMoviesDetailUseCase
): ViewModel() {

    fun getMovieDetail(movieId: Int) {
        getMoviesDetailUseCase.execute(GetMoviesDetailUseCase.Param(movieId = movieId))
            .onEach {
                when(it) {
                    is Resource.Error -> {

                    }
                    Resource.Loading -> {

                    }
                    is Resource.Success -> {

                    }
                }
            }
            .launchIn(viewModelScope)
    }

}