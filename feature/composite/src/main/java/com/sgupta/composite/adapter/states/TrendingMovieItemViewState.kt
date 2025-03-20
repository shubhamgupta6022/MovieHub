package com.sgupta.composite.adapter.states

import com.sgupta.core.ViewState

sealed class TrendingMovieItemViewState : ViewState {
    data class BookmarkClicked(val id: Int, val bookmark: Boolean) : TrendingMovieItemViewState()
    data class ItemClicked(val movieId: Int) : TrendingMovieItemViewState()
}