package com.sgupta.composite.adapter.manager

import com.sgupta.composite.adapter.MovieListItemDelegateAdapter
import com.sgupta.composite.adapter.states.MovieListItemViewState
import com.sgupta.core.delegator.CompositeAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class NowPlayingMoviesAdapterManager @Inject constructor(
    private val movieListItemDelegateAdapter: MovieListItemDelegateAdapter
) {
    fun createCompositeAdapter(): CompositeAdapter {
        return CompositeAdapter.Builder()
            .add(movieListItemDelegateAdapter)
            .build()
    }

    fun createMergedUiStates(): Flow<MovieListItemViewState> {
        return merge(movieListItemDelegateAdapter.uiStates)
    }
}