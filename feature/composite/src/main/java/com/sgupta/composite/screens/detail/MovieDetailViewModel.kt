package com.sgupta.composite.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgupta.composite.mapper.MovieDetailModelMapper
import com.sgupta.composite.model.MovieDetailUiModel
import com.sgupta.core.ViewState
import com.sgupta.core.network.Resource
import com.sgupta.domain.usecase.GetMoviesDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMoviesDetailUseCase: GetMoviesDetailUseCase,
    private val movieDetailModelMapper: MovieDetailModelMapper
) : ViewModel() {

    private val _viewState = MutableStateFlow<DetailViewState>(DetailViewState.Loading)
    val viewState: StateFlow<DetailViewState> = _viewState

    fun getMovieDetail(movieId: Int) {
        getMoviesDetailUseCase.execute(GetMoviesDetailUseCase.Param(movieId = movieId))
            .onEach {
                when (it) {
                    is Resource.Error -> {
                        _viewState.value =
                            DetailViewState.Error(it.error.message ?: "Something went wrong")
                    }

                    Resource.Loading -> {
                        _viewState.value = DetailViewState.Loading
                    }

                    is Resource.Success -> {
                        it.data?.let { result ->
                            _viewState.value = DetailViewState.Success(
                                movieDetailModelMapper.convert(result)
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}

sealed class DetailViewState : ViewState {
    data object Loading : DetailViewState()
    data class Success(
        val model: MovieDetailUiModel
    ) : DetailViewState()

    data class Error(val message: String) : DetailViewState()
}