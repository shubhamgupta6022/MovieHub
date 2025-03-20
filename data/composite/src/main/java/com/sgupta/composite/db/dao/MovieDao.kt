package com.sgupta.composite.db.dao

import androidx.room.*
import com.sgupta.composite.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE type = :type")
    fun getMoviesByType(type: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE bookmark = 1")
    fun getBookmarkedMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update
    suspend fun updateMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE id IN (:movieIds)")
    suspend fun getMoviesByIds(movieIds: List<Int>): List<MovieEntity>

    @Query("DELETE FROM movies WHERE type = :type AND id NOT IN (:keepIds)")
    suspend fun deleteMoviesExcept(type: String, keepIds: List<Int>)

    @Query("UPDATE movies SET bookmark = :isBookmarked WHERE id = :movieId")
    suspend fun updateBookmarkStatus(movieId: Int, isBookmarked: Boolean)
}