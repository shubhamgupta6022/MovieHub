package com.sgupta.domain.model

data class MovieDetailDomainModel(
    val isAdult: Boolean,
    val backdropUrl: String,
    val belongsToCollection: BelongsToCollectionDomainModel?,
    val budget: Int,
    val genres: List<GenreDomainModel>,
    val homepage: String,
    val id: Int,
    val imdbId: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterUrl: String,
    val productionCompanies: List<ProductionCompanyDomainModel>,
    val productionCountries: List<ProductionCountryDomainModel>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguageDomainModel>,
    val status: String,
    val tagline: String,
    val title: String,
    val isVideo: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

data class BelongsToCollectionDomainModel(
    val backdropUrl: String,
    val id: Int,
    val name: String,
    val posterUrl: String
)

data class GenreDomainModel(
    val id: Int,
    val name: String
)

data class ProductionCompanyDomainModel(
    val id: Int,
    val logoUrl: String,
    val name: String,
    val originCountry: String
)

data class ProductionCountryDomainModel(
    val iso31661: String,
    val name: String
)

data class SpokenLanguageDomainModel(
    val englishName: String,
    val iso6391: String,
    val name: String
)
