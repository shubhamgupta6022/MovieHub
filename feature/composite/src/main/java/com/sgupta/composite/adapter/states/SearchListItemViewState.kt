package com.sgupta.composite.adapter.states

import com.sgupta.core.ViewState

sealed class SearchListItemViewState : ViewState {
    data class ItemClicked(val movieId: Int) : SearchListItemViewState()
}