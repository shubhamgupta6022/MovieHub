package com.sgupta.composite.screens.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgupta.composite.adapter.states.MovieListItemViewState
import com.sgupta.composite.mapper.MovieItemDomainModelMapper
import com.sgupta.composite.model.MovieListItemUiModel
import com.sgupta.core.ViewState
import com.sgupta.core.delegator.DelegateAdapterItem
import com.sgupta.core.network.Resource
import com.sgupta.domain.usecase.GetBookmarkedMoviesUseCase
import com.sgupta.domain.usecase.SetBookmarkedMovieStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookmarkFragmentViewModel @Inject constructor(
    private val bookmarkedMoviesUseCase: GetBookmarkedMoviesUseCase,
    private val movieItemDomainModelMapper: MovieItemDomainModelMapper,
    private val setBookmarkedMovieStatusUseCase: SetBookmarkedMovieStatusUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<BookmarkViewState>(BookmarkViewState.Loading)
    val viewState: StateFlow<BookmarkViewState> = _viewState

    private var bookmarkList: MutableList<DelegateAdapterItem> = mutableListOf()

    init {
        getBookmarkedMoviesUseCase()
    }

    private fun getBookmarkedMoviesUseCase() {
        bookmarkedMoviesUseCase.execute(GetBookmarkedMoviesUseCase.Param(1))
            .onEach {
                when (it) {
                    is Resource.Loading -> {
                        _viewState.value = BookmarkViewState.Loading
                    }

                    is Resource.Error -> {

                    }

                    is Resource.Success -> {
                        if (it.data == null) {
                            _viewState.value = BookmarkViewState.NoData
                        } else {
                            val items = mutableListOf<DelegateAdapterItem>()
                            val listItems =
                                it.data?.movieItemResponses.orEmpty().toMutableList()
                            listItems.let { trendingMovies ->
                                items.addAll(trendingMovies.map { movie ->
                                    movieItemDomainModelMapper.convert(movie)
                                })
                            }
                            bookmarkList = items.toMutableList()
                            _viewState.value = BookmarkViewState.Success(items)
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
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

                // Remove the unbookmarked item from the list
                val updatedList = bookmarkList.filterNot {
                    (it is MovieListItemUiModel) && (it.id == state.id)
                }.toMutableList()

                // Update the bookmark list and emit new state
                bookmarkList = updatedList
                _viewState.value = if (updatedList.isEmpty()) {
                    BookmarkViewState.NoData
                } else {
                    BookmarkViewState.Success(updatedList)
                }
            }
        }
    }
}

sealed class BookmarkViewState : ViewState {
    data object Loading : BookmarkViewState()
    data class Success(
        val items: List<DelegateAdapterItem>
    ) : BookmarkViewState()

    data object NoData : BookmarkViewState()
}