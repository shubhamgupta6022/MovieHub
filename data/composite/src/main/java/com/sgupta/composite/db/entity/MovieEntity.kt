package com.sgupta.composite.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sgupta.domain.model.MovieItemDomainModel

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val type: String,
    val title: String?,
    val overview: String?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val popularity: Double?,
    val lastUpdated: Long = System.currentTimeMillis()
) {
    fun toDomainModel() = MovieItemDomainModel(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl,
        backdropUrl = backdropUrl,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        popularity = popularity
    )

    companion object {
        fun fromDomainModel(model: MovieItemDomainModel, type: String) = MovieEntity(
            id = model.id ?: 0,
            type = type,
            title = model.title,
            overview = model.overview,
            posterUrl = model.posterUrl,
            backdropUrl = model.backdropUrl,
            releaseDate = model.releaseDate,
            voteAverage = model.voteAverage,
            popularity = model.popularity
        )
    }
}

enum class MovieCategory(name: String) {
    TRENDING("TRENDING"),
    NOW_PLAYING("NOW_PLAYING")
}