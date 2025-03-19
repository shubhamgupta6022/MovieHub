package com.sgupta.composite.adapter.manager

import com.sgupta.composite.adapter.TrendingMovieDelegateAdapter
import com.sgupta.composite.adapter.states.TrendingMovieItemViewState
import com.sgupta.core.delegator.CompositeAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class TrendingMoviesAdapterManager @Inject constructor(
    private val trendingMovieDelegateAdapter: TrendingMovieDelegateAdapter
) {
    fun createCompositeAdapter(): CompositeAdapter {
        return CompositeAdapter.Builder()
            .add(trendingMovieDelegateAdapter)
            .build()
    }

    fun createCompositeAdapterUiStates(): Flow<TrendingMovieItemViewState> {
        return merge(trendingMovieDelegateAdapter.uiStates)
    }
}