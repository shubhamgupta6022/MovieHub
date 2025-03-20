package com.sgupta.domain.usecase

import com.sgupta.core.flows.onError
import com.sgupta.core.flows.onLoading
import com.sgupta.core.flows.onSuccess
import com.sgupta.core.network.Resource
import com.sgupta.core.usecase.QueryUseCase
import com.sgupta.domain.model.MovieListDomainModel
import com.sgupta.domain.repo.MovieHubRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesQueryUseCase @Inject constructor(
    private val movieHubRepo: MovieHubRepo
) : QueryUseCase<GetMoviesQueryUseCase.Param, Resource<MovieListDomainModel>>() {

    override fun start(param: Param): Flow<Resource<MovieListDomainModel>> = flow {
        movieHubRepo.getMovieQuery(param.query)
            .onLoading {
                emit(Resource.Loading)
            }
            .onSuccess {
                emit(Resource.Success(it))
            }
            .onError {
                emit(Resource.Error(it))
            }
            .collect()
    }

    data class Param(val query: String)
}