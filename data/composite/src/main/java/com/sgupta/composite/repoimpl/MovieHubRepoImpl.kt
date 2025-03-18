package com.sgupta.composite.repoimpl

import com.sgupta.composite.api.MovieHubAPIService
import com.sgupta.composite.model.toMovieListDomainModel
import com.sgupta.core.flows.toResponseFlow
import com.sgupta.core.network.Resource
import com.sgupta.domain.model.MovieListDomainModel
import com.sgupta.domain.repo.MovieHubRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieHubRepoImpl @Inject constructor(
    private val apiService: MovieHubAPIService
) : MovieHubRepo {
    override fun getTrendingMovies(): Flow<Resource<MovieListDomainModel>> {
        return toResponseFlow(
            apiCall = {
                apiService.getTrendingMovies()
            },
            mapper = {
                it?.toMovieListDomainModel()
            }
        )
    }
}