package com.sgupta.domain.model

data class MovieListDomainModel(
    val page: Int,
    val movieItemResponses: List<MovieItemDomainModel>? = null,
    val totalPages: Int,
    val totalResults: Int
)

data class MovieItemDomainModel(
    val adult: Boolean? = null,
    val backdropUrl: String? = null,
    val genreIds: List<Int>? = null,
    val id: Int? = null,
    val mediaType: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterUrl: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
    val bookmark: Boolean = false
)