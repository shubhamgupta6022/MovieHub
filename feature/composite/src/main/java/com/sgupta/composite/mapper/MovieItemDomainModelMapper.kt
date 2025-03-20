package com.sgupta.composite.mapper

import com.sgupta.core.mapper.Mapper
import com.sgupta.domain.model.MovieItemDomainModel
import com.sgupta.composite.model.MovieListItemUiModel
import javax.inject.Inject

class MovieItemDomainModelMapper @Inject constructor() : Mapper<MovieItemDomainModel, MovieListItemUiModel> {
    
    override fun convert(from: MovieItemDomainModel): MovieListItemUiModel {
        val title = if (from.name?.isNotBlank() == true) {
            from.name.orEmpty()
        } else {
            from.title.orEmpty()
        }
        return MovieListItemUiModel(
            id = from.id ?: 0,
            title = title,
            rating = String.format("%.1f", from.voteAverage ?: 0.0),
            year = from.releaseDate?.substring(0, 4)?.toIntOrNull() ?: 0,
            posterUrl = from.posterUrl.orEmpty(),
            bookmark = from.bookmark,
            overview = from.overview.orEmpty()
        )
    }
}