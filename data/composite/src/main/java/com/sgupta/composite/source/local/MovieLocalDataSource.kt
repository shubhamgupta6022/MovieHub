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
        // Convert domain models to entities
        val newEntities = movies.map { MovieEntity.fromDomainModel(it, type) }
        
        // Get IDs of new movies
        val newMovieIds = newEntities.map { it.id }
        
        // Get existing movies from DB
        val existingMovies = movieDao.getMoviesByIds(newMovieIds)
        val existingMovieIds = existingMovies.map { it.id }
        
        // Split new entities into ones to insert and ones to update
        val (toInsert, toUpdate) = newEntities.partition { entity -> 
            entity.id !in existingMovieIds 
        }
        
        // For updating, preserve bookmark status from existing movies
        val updatedEntities = toUpdate.map { newEntity ->
            val existingEntity = existingMovies.first { it.id == newEntity.id }
            newEntity.copy(bookmark = existingEntity.bookmark)
        }
        
        // Insert new movies
        movieDao.insertMovies(toInsert)
        
        // Update existing movies while preserving bookmark status
        movieDao.updateMovies(updatedEntities)
        
        // Clean up old movies that are no longer in the list
        movieDao.deleteMoviesExcept(type, newMovieIds)
    }

    suspend fun updateBookmarkStatus(movieId: Int, isBookmarked: Boolean) {
        movieDao.updateBookmarkStatus(movieId, isBookmarked)
    }

}