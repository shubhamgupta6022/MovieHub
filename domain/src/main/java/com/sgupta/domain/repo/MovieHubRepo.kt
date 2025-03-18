package com.sgupta.domain.repo

import com.sgupta.core.network.Resource
import kotlinx.coroutines.flow.Flow

interface MovieHubRepo {
    fun getTrendingMovies(): Flow<Resource<Any>>
}