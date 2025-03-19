package com.sgupta.composite.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgupta.composite.model.MovieListItemUiModel
import com.sgupta.composite.model.TrendingMovieUiModel
import com.sgupta.core.ViewState
import com.sgupta.core.delegator.DelegateAdapterItem
import com.sgupta.core.network.Resource
import com.sgupta.domain.usecase.GetNowPlayingMoviesUseCase
import com.sgupta.domain.usecase.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewState: StateFlow<HomeViewState> = _viewState

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        val trendingMoviesFlow = getTrendingMoviesUseCase.execute(GetTrendingMoviesUseCase.Param(1))
        val nowPlayingMoviesFlow =
            getNowPlayingMoviesUseCase.execute(GetNowPlayingMoviesUseCase.Param(1))

        combine(
            trendingMoviesFlow,
            nowPlayingMoviesFlow
        ) { trendingResult, nowPlayingResult ->
            when {
                trendingResult is Resource.Loading || nowPlayingResult is Resource.Loading -> {
                    _viewState.value = HomeViewState.Loading
                }

                trendingResult is Resource.Error -> {
                    _viewState.value = HomeViewState.Error(trendingResult.error.message.orEmpty())
                }

                nowPlayingResult is Resource.Error -> {
                    _viewState.value = HomeViewState.Error(nowPlayingResult.error.message.orEmpty())
                }

                trendingResult is Resource.Success && nowPlayingResult is Resource.Success -> {
                    val trendingItems = mutableListOf<DelegateAdapterItem>()
                    val nowPlayingItems = mutableListOf<DelegateAdapterItem>()

                    // Add trending movies
                    trendingResult.data?.movieItemResponses?.let { trendingMovies ->
                        trendingItems.addAll(trendingMovies.map { movie ->
                            TrendingMovieUiModel(
                                id = movie.id ?: 0,
                                title = movie.title.orEmpty(),
                                rating = movie.voteAverage ?: 0.0,
                                year = movie.releaseDate?.substring(0, 4)?.toIntOrNull() ?: 0,
                                posterUrl = movie.posterPath.orEmpty()
                            )
                        })
                    }

                    // Add now playing movies
                    nowPlayingResult.data?.movieItemResponses?.let { nowPlayingMovies ->
                        nowPlayingItems.addAll(nowPlayingMovies.map { movie ->
                            MovieListItemUiModel(
                                id = movie.id ?: 0,
                                title = movie.title.orEmpty(),
                                rating = movie.voteAverage ?: 0.0,
                                year = movie.releaseDate?.substring(0, 4)?.toIntOrNull() ?: 0,
                                posterUrl = movie.posterPath.orEmpty()
                            )
                        })
                    }

                    _viewState.value = HomeViewState.Success(trendingItems, nowPlayingItems)
                }
            }
        }.launchIn(viewModelScope)
    }
}

sealed class HomeViewState : ViewState {
    data object Loading : HomeViewState()
    data class Success(
        val trendingItems: List<DelegateAdapterItem>,
        val nowPlayingItems: List<DelegateAdapterItem>
    ) : HomeViewState()

    data class Error(val error: String) : HomeViewState()
}