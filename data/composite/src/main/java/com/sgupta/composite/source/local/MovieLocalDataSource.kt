package com.sgupta.composite.source.local

import com.sgupta.composite.db.dao.MovieDao
import com.sgupta.composite.db.entity.MovieEntity
import com.sgupta.domain.model.MovieItemDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {
    fun getMoviesByType(type: String): Flow<List<MovieItemDomainModel>> {
        return movieDao.getMoviesByType(type)
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    suspend fun saveMovies(movies: List<MovieItemDomainModel>, type: String) {
        movieDao.insertMovies(movies.map { MovieEntity.fromDomainModel(it, type) })
    }
}