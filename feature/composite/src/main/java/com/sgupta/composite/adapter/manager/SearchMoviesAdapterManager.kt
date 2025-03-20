package com.sgupta.composite.adapter.manager

import com.sgupta.composite.adapter.MovieSearchItemDelegateAdapter
import com.sgupta.composite.adapter.states.MovieListItemViewState
import com.sgupta.core.delegator.CompositeAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class SearchMoviesAdapterManager @Inject constructor(
    private val movieSearchItemDelegateAdapter: MovieSearchItemDelegateAdapter
) {
    fun createCompositeAdapter(): CompositeAdapter {
        return CompositeAdapter.Builder()
            .add(movieSearchItemDelegateAdapter)
            .build()
    }

    fun createMergedUiStates(): Flow<MovieListItemViewState> {
        return merge(movieSearchItemDelegateAdapter.uiStates)
    }
}