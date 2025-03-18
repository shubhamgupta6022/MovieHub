package com.sgupta.composite.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgupta.core.network.Resource
import com.sgupta.domain.usecase.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
) : ViewModel() {
    init {
        getTrendingMovies()
    }

    fun getTrendingMovies() {
        getTrendingMoviesUseCase.execute(GetTrendingMoviesUseCase.Param(1))
            .onEach {
                when (it) {
                    is Resource.Success -> {
                        Log.d("HomeViewModel", "Success = ${it.data}")
                    }

                    is Resource.Error -> {
                        Log.d("HomeViewModel", "Error = ${it.error}")
                    }

                    Resource.Loading -> {
                        Log.d("HomeViewModel", "Loading")
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}