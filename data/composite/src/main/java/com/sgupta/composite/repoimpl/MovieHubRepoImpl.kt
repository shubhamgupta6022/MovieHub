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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieHubRepoImpl @Inject constructor(
    private val apiService: MovieHubAPIService,
    private val localDataSource: MovieLocalDataSource
) : MovieHubRepo {

    override fun getTrendingMovies(): Flow<Resource<MovieListDomainModel>> = flow {
        emit(Resource.Loading)

        // Step 1: Emit data from local cache if available
        val cachedMovies = localDataSource.getMoviesByType(MovieCategory.TRENDING.name).first()
        if (cachedMovies.isNotEmpty()) {
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

        // Step 2: Fetch from network and save to local DB
        toResponseFlow(
            apiCall = { apiService.getTrendingMovies() },
            mapper = { it?.toMovieListDomainModel() }
        ).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.movieItemResponses?.let { movies ->
                        localDataSource.saveMovies(movies, MovieCategory.TRENDING.name)
                    }

                    // Step 3: Fetch from local DB again and emit updated data
                    val updatedMovies = localDataSource.getMoviesByType(MovieCategory.TRENDING.name).first()
                    emit(
                        Resource.Success(
                            MovieListDomainModel(
                                page = 1,
                                movieItemResponses = updatedMovies,
                                totalPages = 1,
                                totalResults = updatedMovies.size
                            )
                        )
                    )
                }
                is Resource.Error -> emit(resource)
                Resource.Loading -> { /* Do nothing */ }
            }
        }
    }

    override fun getNowPlayingMovies(page: Int): Flow<Resource<MovieListDomainModel>> = flow {
        emit(Resource.Loading)

        // Step 1: Emit data from local cache if available
        val cachedMovies = localDataSource.getMoviesByType(MovieCategory.NOW_PLAYING.name).first()
        if (cachedMovies.isNotEmpty()) {
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

        // Step 2: Fetch from network and save to local DB if page is 1
        toResponseFlow(
            apiCall = { apiService.getNowPlayingMovies(page = page) },
            mapper = { it?.toMovieListDomainModel() }
        ).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.movieItemResponses?.let { movies ->
                        if (page == 1) {
                            localDataSource.saveMovies(movies, MovieCategory.NOW_PLAYING.name)
                        }
                    }

                    // Step 3: Fetch from local DB again and emit updated data if it's page 1
                    if (page == 1) {
                        val updatedMovies = localDataSource.getMoviesByType(MovieCategory.NOW_PLAYING.name).first()
                        emit(
                            Resource.Success(
                                MovieListDomainModel(
                                    page = resource.data?.page ?: 1,
                                    movieItemResponses = updatedMovies,
                                    totalPages = resource.data?.totalPages ?: 1,
                                    totalResults = resource.data?.totalResults ?: 1
                                )
                            )
                        )
                    } else {
                        emit(resource)
                    }
                }
                is Resource.Error -> emit(resource)
                Resource.Loading -> { /* Do nothing */ }
            }
        }
    }
}