package com.sgupta.domain.repo

import com.sgupta.core.network.Resource
import com.sgupta.domain.model.MovieDetailDomainModel
import com.sgupta.domain.model.MovieListDomainModel
import kotlinx.coroutines.flow.Flow

interface MovieHubRepo {
    fun getTrendingMovies(): Flow<Resource<MovieListDomainModel>>
    fun getNowPlayingMovies(page: Int): Flow<Resource<MovieListDomainModel>>
    fun getBookmarkList(): Flow<Resource<MovieListDomainModel>>
    suspend fun setBookmarkStatus(id: Int, bookmark: Boolean)
    fun getMovieQuery(query: String): Flow<Resource<MovieListDomainModel>>
    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetailDomainModel>>
}