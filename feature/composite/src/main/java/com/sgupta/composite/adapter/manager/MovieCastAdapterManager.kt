package com.sgupta.composite.adapter.manager

import com.sgupta.composite.adapter.MovieCastItemDelegateAdapter
import com.sgupta.composite.adapter.MovieListItemDelegateAdapter
import com.sgupta.composite.adapter.states.MovieListItemViewState
import com.sgupta.core.delegator.CompositeAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class MovieCastAdapterManager @Inject constructor(
    private val movieCastItemDelegateAdapter: MovieCastItemDelegateAdapter
) {
    fun createCompositeAdapter(): CompositeAdapter {
        return CompositeAdapter.Builder()
            .add(movieCastItemDelegateAdapter)
            .build()
    }
}