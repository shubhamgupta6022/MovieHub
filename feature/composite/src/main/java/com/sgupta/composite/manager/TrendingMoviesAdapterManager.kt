package com.sgupta.composite.manager

import com.sgupta.composite.adapter.TrendingMovieDelegateAdapter
import com.sgupta.core.delegator.CompositeAdapter
import javax.inject.Inject

class TrendingMoviesAdapterManager @Inject constructor(
    private val trendingMovieDelegateAdapter: TrendingMovieDelegateAdapter
) {
    fun createCompositeAdapter(): CompositeAdapter {
        return CompositeAdapter.Builder()
            .add(trendingMovieDelegateAdapter)
            .build()
    }
}