package com.sgupta.domain.usecase

import com.sgupta.core.network.Resource
import com.sgupta.core.usecase.QueryUseCase
import com.sgupta.domain.model.MovieListDomainModel
import com.sgupta.domain.repo.MovieHubRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetBookmarkedMovieStatusUseCase @Inject constructor(
    private val movieHubRepo: MovieHubRepo
) : QueryUseCase<SetBookmarkedMovieStatusUseCase.Param, Resource<MovieListDomainModel>>() {

    override fun start(param: Param): Flow<Resource<MovieListDomainModel>> = flow {
        movieHubRepo.setBookmarkStatus(param.movieId, param.bookmark)
    }

    data class Param(val movieId: Int, val bookmark: Boolean)
}