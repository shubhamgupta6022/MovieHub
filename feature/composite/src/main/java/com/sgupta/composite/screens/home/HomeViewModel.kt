package com.sgupta.composite.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgupta.composite.adapter.states.MovieListItemViewState
import com.sgupta.composite.adapter.states.TrendingMovieItemViewState
import com.sgupta.composite.mapper.MovieItemDomainModelMapper
import com.sgupta.composite.mapper.MovieListModelUpdateMapper
import com.sgupta.composite.mapper.TrendingMovieDomainModelMapper
import com.sgupta.core.ViewState
import com.sgupta.core.delegator.DelegateAdapterItem
import com.sgupta.core.network.Resource
import com.sgupta.domain.model.MovieItemDomainModel
import com.sgupta.domain.usecase.GetNowPlayingMoviesUseCase
import com.sgupta.domain.usecase.GetTrendingMoviesUseCase
import com.sgupta.domain.usecase.SetBookmarkedMovieStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val movieItemDomainModelMapper: MovieItemDomainModelMapper,
    private val trendingMovieDomainModelMapper: TrendingMovieDomainModelMapper,
    private val movieListModelUpdateMapper: MovieListModelUpdateMapper,
    private val setBookmarkedMovieStatusUseCase: SetBookmarkedMovieStatusUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewState: StateFlow<HomeViewState> = _viewState

    private var trendingListItems: MutableList<MovieItemDomainModel> = mutableListOf()
    private var nowPlayingListItems: MutableList<MovieItemDomainModel> = mutableListOf()

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
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

                    trendingListItems =
                        trendingResult.data?.movieItemResponses.orEmpty().toMutableList()
                    trendingListItems.let { trendingMovies ->
                        trendingItems.addAll(trendingMovies.map { movie ->
                            trendingMovieDomainModelMapper.convert(movie)
                        })
                    }

                    nowPlayingListItems =
                        nowPlayingResult.data?.movieItemResponses.orEmpty().toMutableList()
                    nowPlayingListItems.let { nowPlayingMovies ->
                        nowPlayingItems.addAll(nowPlayingMovies.map { movie ->
                            movieItemDomainModelMapper.convert(movie)
                        })
                    }

                    _viewState.value = HomeViewState.Success(trendingItems, nowPlayingItems)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateState(state: ViewState) {
        when (state) {
            is MovieListItemViewState.BookmarkClicked -> {
                setBookmarkedMovieStatusUseCase.execute(
                    SetBookmarkedMovieStatusUseCase.Param(
                        state.id,
                        !state.bookmark
                    )
                )
                    .launchIn(viewModelScope)
                val updateModel = movieListModelUpdateMapper.convert(
                    MovieListModelUpdateMapper.Param(
                        state,
                        nowPlayingListItems
                    )
                )
                nowPlayingListItems = updateModel.toMutableList()

                val nowPlayingItems = mutableListOf<DelegateAdapterItem>()
                nowPlayingListItems.let { nowPlayingMovies ->
                    nowPlayingItems.addAll(nowPlayingMovies.map { movie ->
                        movieItemDomainModelMapper.convert(movie)
                    })
                }

                _viewState.value = HomeViewState.NowPlayingItemsUpdated(nowPlayingItems)
            }

            is TrendingMovieItemViewState.BookmarkClicked -> {
                setBookmarkedMovieStatusUseCase.execute(
                    SetBookmarkedMovieStatusUseCase.Param(
                        state.id,
                        !state.bookmark
                    )
                )
                    .launchIn(viewModelScope)
                val updateModel = movieListModelUpdateMapper.convert(
                    MovieListModelUpdateMapper.Param(
                        state,
                        trendingListItems
                    )
                )
                trendingListItems = updateModel.toMutableList()

                val trendingItems = mutableListOf<DelegateAdapterItem>()
                trendingListItems.let { trendingMovies ->
                    trendingItems.addAll(trendingMovies.map { movie ->
                        trendingMovieDomainModelMapper.convert(movie)
                    })
                }
                _viewState.value = HomeViewState.TrendingMovieItemsUpdated(trendingItems)
            }
        }
    }
}

sealed class HomeViewState : ViewState {
    data object Loading : HomeViewState()
    data class Success(
        val trendingItems: List<DelegateAdapterItem>,
        val nowPlayingItems: List<DelegateAdapterItem>
    ) : HomeViewState()

    data class Error(val error: String) : HomeViewState()
    data class NowPlayingItemsUpdated(val list: MutableList<DelegateAdapterItem>) : HomeViewState()
    data class TrendingMovieItemsUpdated(val list: MutableList<DelegateAdapterItem>) :
        HomeViewState()
}