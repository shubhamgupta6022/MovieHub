package com.sgupta.composite.model

import com.google.gson.annotations.SerializedName
import com.sgupta.core.IMAGE_BASE_URL
import com.sgupta.domain.model.BelongsToCollectionDomainModel
import com.sgupta.domain.model.GenreDomainModel
import com.sgupta.domain.model.MovieDetailDomainModel
import com.sgupta.domain.model.ProductionCompanyDomainModel
import com.sgupta.domain.model.ProductionCountryDomainModel
import com.sgupta.domain.model.SpokenLanguageDomainModel

data class MovieDetailAPIResponse(
    @SerializedName("adult") val isAdult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("belongs_to_collection") val belongsToCollection: BelongsToCollection?,
    @SerializedName("budget") val budget: Int?,
    @SerializedName("genres") val genres: List<Genre>?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("origin_country") val originCountry: List<String>?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompany>?,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountry>?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("revenue") val revenue: Int?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguage>?,
    @SerializedName("status") val status: String?,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("video") val isVideo: Boolean?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
)

data class BelongsToCollection(
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("poster_path") val posterPath: String?
)

data class Genre(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?
)

data class ProductionCompany(
    @SerializedName("id") val id: Int?,
    @SerializedName("logo_path") val logoPath: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("origin_country") val originCountry: String?
)

data class ProductionCountry(
    @SerializedName("iso_3166_1") val iso31661: String?,
    @SerializedName("name") val name: String?
)

data class SpokenLanguage(
    @SerializedName("english_name") val englishName: String?,
    @SerializedName("iso_639_1") val iso6391: String?,
    @SerializedName("name") val name: String?
)

fun MovieDetailAPIResponse.toMovieDetailDomainModel() = MovieDetailDomainModel(
    isAdult = isAdult ?: false,
    backdropUrl = backdropPath?.let { "$IMAGE_BASE_URL$it" } ?: "",
    belongsToCollection = belongsToCollection?.toBelongsToCollectionDomainModel(),
    budget = budget ?: 0,
    genres = genres?.map { it.toGenreDomainModel() } ?: emptyList(),
    homepage = homepage.orEmpty(),
    id = id ?: 0,
    imdbId = imdbId.orEmpty(),
    originCountry = originCountry.orEmpty(),
    originalLanguage = originalLanguage.orEmpty(),
    originalTitle = originalTitle.orEmpty(),
    overview = overview.orEmpty(),
    popularity = popularity ?: 0.0,
    posterUrl = posterPath?.let { "$IMAGE_BASE_URL$it" } ?: "",
    productionCompanies = productionCompanies?.map { it.toProductionCompanyDomainModel() } ?: emptyList(),
    productionCountries = productionCountries?.map { it.toProductionCountryDomainModel() } ?: emptyList(),
    releaseDate = releaseDate.orEmpty(),
    revenue = revenue ?: 0,
    runtime = runtime ?: 0,
    spokenLanguages = spokenLanguages?.map { it.toSpokenLanguageDomainModel() } ?: emptyList(),
    status = status.orEmpty(),
    tagline = tagline.orEmpty(),
    title = title.orEmpty(),
    isVideo = isVideo ?: false,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0
)

fun BelongsToCollection.toBelongsToCollectionDomainModel() = BelongsToCollectionDomainModel(
    backdropUrl = backdropPath?.let { "$IMAGE_BASE_URL$it" } ?: "",
    id = id ?: 0,
    name = name.orEmpty(),
    posterUrl = posterPath?.let { "$IMAGE_BASE_URL$it" } ?: ""
)

fun Genre.toGenreDomainModel() = GenreDomainModel(
    id = id ?: 0,
    name = name.orEmpty()
)

fun ProductionCompany.toProductionCompanyDomainModel() = ProductionCompanyDomainModel(
    id = id ?: 0,
    logoUrl = logoPath?.let { "$IMAGE_BASE_URL$it" } ?: "",
    name = name.orEmpty(),
    originCountry = originCountry.orEmpty()
)

fun ProductionCountry.toProductionCountryDomainModel() = ProductionCountryDomainModel(
    iso31661 = iso31661.orEmpty(),
    name = name.orEmpty()
)

fun SpokenLanguage.toSpokenLanguageDomainModel() = SpokenLanguageDomainModel(
    englishName = englishName.orEmpty(),
    iso6391 = iso6391.orEmpty(),
    name = name.orEmpty()
)

