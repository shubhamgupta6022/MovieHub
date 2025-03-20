package com.sgupta.domain.repo

import com.sgupta.core.network.Resource
import com.sgupta.domain.model.MovieListDomainModel
import kotlinx.coroutines.flow.Flow

interface MovieHubRepo {
    fun getTrendingMovies(): Flow<Resource<MovieListDomainModel>>
    fun getNowPlayingMovies(page: Int): Flow<Resource<MovieListDomainModel>>
    fun getBookmarkList(): Flow<Resource<MovieListDomainModel>>
    suspend fun setBookmarkStatus(id: Int, bookmark: Boolean)
}