package com.sgupta.composite.model

data class MovieDetailUiModel(
    val title: String,
    val backdropUrl: String,
    val postUrl: String,
    val overview: String,
    val cast: List<MovieCastItemUiModel>
)