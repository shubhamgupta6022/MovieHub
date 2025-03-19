package com.sgupta.composite.repoimpl

import com.sgupta.composite.db.entity.MovieCategory
import com.sgupta.composite.model.toMovieListDomainModel
import com.sgupta.composite.source.local.MovieLocalDataSource
import com.sgupta.composite.source.remote.MovieHubAPIService
import com.sgupta.core.flows.toResponseFlow
import com.sgupta.core.network.Resource
import com.sgupta.domain.model.MovieListDomainModel
import com.sgupta.domain.repo.MovieHubRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieHubRepoImpl @Inject constructor(
    private val apiService: MovieHubAPIService,
    private val localDataSource: MovieLocalDataSource
) : MovieHubRepo {

    override fun getTrendingMovies(): Flow<Resource<MovieListDomainModel>> = flow {
        emit(Resource.Loading)

        localDataSource.getMoviesByType(MovieCategory.TRENDING.name)
            .collect { cachedMovies ->
                emit(
                    Resource.Success(
                        MovieListDomainModel(
                            page = 1,
                            movieItemResponses = cachedMovies,
                            totalPages = 1,
                            totalResults = cachedMovies.size
                        )
                    )
                )
            }

        // Fetch from network using toResponseFlow
        toResponseFlow(
            apiCall = { apiService.getTrendingMovies() },
            mapper = { it?.toMovieListDomainModel() }
        ).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.movieItemResponses?.let { movies ->
                        localDataSource.saveMovies(movies, MovieCategory.TRENDING.name)
                    }
                    emit(resource)
                }

                is Resource.Error -> emit(resource)
                Resource.Loading -> { /* Already emitted loading state */ }
            }
        }
    }

    override fun getNowPlayingMovies(page: Int): Flow<Resource<MovieListDomainModel>> = flow {
        emit(Resource.Loading)
        localDataSource.getMoviesByType(MovieCategory.NOW_PLAYING.name)
            .collect { cachedMovies ->
                emit(
                    Resource.Success(
                        MovieListDomainModel(
                            page = 1,
                            movieItemResponses = cachedMovies,
                            totalPages = 1,
                            totalResults = cachedMovies.size
                        )
                    )
                )
            }

        // Fetch from network using toResponseFlow
        toResponseFlow(
            apiCall = { apiService.getNowPlayingMovies(page = page) },
            mapper = { it?.toMovieListDomainModel() }
        ).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    // Cache the new data only for page 1
                    if (page == 1) {
                        resource.data?.movieItemResponses?.let { movies ->
                            localDataSource.saveMovies(movies, MovieCategory.NOW_PLAYING.name)
                        }
                    }
                    emit(resource)
                }

                is Resource.Error -> emit(resource)
                Resource.Loading -> { /* Already emitted loading state */ }
            }
        }
    }
}