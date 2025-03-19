package com.sgupta.composite.manager

import com.sgupta.composite.adapter.MovieListItemDelegateAdapter
import com.sgupta.core.delegator.CompositeAdapter
import javax.inject.Inject

class NowPlayingMoviesAdapterManager @Inject constructor(
    private val movieListItemDelegateAdapter: MovieListItemDelegateAdapter
) {
    fun createCompositeAdapter(): CompositeAdapter {
        return CompositeAdapter.Builder()
            .add(movieListItemDelegateAdapter)
            .build()
    }
}