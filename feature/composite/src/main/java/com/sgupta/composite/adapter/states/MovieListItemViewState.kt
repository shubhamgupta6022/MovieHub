package com.sgupta.composite.adapter.states

import com.sgupta.core.ViewState

sealed class MovieListItemViewState : ViewState {
    data class BookmarkClicked(val id: Int, val bookmark: Boolean) : MovieListItemViewState()
    data class ItemClicked(val movieId: Int) : MovieListItemViewState()
}