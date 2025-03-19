package com.sgupta.composite.mapper

import com.sgupta.core.mapper.Mapper
import com.sgupta.domain.model.MovieItemDomainModel
import com.sgupta.composite.model.MovieListItemUiModel
import com.sgupta.composite.model.TrendingMovieUiModel
import javax.inject.Inject

class TrendingMovieDomainModelMapper @Inject constructor() : Mapper<MovieItemDomainModel, TrendingMovieUiModel> {
    
    override fun convert(from: MovieItemDomainModel): TrendingMovieUiModel {
        return TrendingMovieUiModel(
            id = from.id ?: 0,
            title = from.title.orEmpty(),
            rating = String.format("%.1f", from.voteAverage ?: 0.0),
            year = from.releaseDate?.substring(0, 4)?.toIntOrNull() ?: 0,
            posterUrl = from.posterUrl.orEmpty()
        )
    }
}