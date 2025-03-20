package com.sgupta.composite.model

import com.google.gson.annotations.SerializedName
import com.sgupta.core.IMAGE_BASE_URL
import com.sgupta.domain.model.MovieItemDomainModel
import com.sgupta.domain.model.MovieListDomainModel

data class MovieAPIResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movieItemResponses: List<MovieItemResponse>? = null,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class MovieItemResponse(
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("genre_ids") val genreIds: List<Int>? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("media_type") val mediaType: String? = null,
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("video") val video: Boolean? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
    @SerializedName("vote_count") val voteCount: Int? = null,
    @SerializedName("name") val name: String? = null,
)

fun MovieAPIResponse.toMovieListDomainModel() = MovieListDomainModel(
    page = page,
    movieItemResponses = movieItemResponses?.map { it.toMovieItemDomainModel() },
    totalPages = totalPages,
    totalResults = totalResults
)

fun MovieItemResponse.toMovieItemDomainModel() = MovieItemDomainModel(
    adult = adult,
    backdropUrl = "$IMAGE_BASE_URL$backdropPath",
    genreIds = genreIds,
    id = id,
    mediaType = mediaType,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterUrl = "$IMAGE_BASE_URL$posterPath",
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
    name = name
)