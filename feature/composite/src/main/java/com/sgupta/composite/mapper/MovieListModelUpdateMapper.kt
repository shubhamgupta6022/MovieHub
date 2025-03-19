package com.sgupta.composite.mapper

import com.sgupta.composite.adapter.states.MovieListItemViewState
import com.sgupta.composite.adapter.states.TrendingMovieItemViewState
import com.sgupta.core.ViewState
import com.sgupta.core.mapper.Mapper
import com.sgupta.domain.model.MovieItemDomainModel
import javax.inject.Inject

class MovieListModelUpdateMapper @Inject constructor() :
    Mapper<MovieListModelUpdateMapper.Param, List<MovieItemDomainModel>> {

    override fun convert(from: Param): List<MovieItemDomainModel> {
        return when (val state = from.state) {
            is MovieListItemViewState.BookmarkClicked -> {
                from.oldData.map { movie ->
                    if (movie.id == state.id) {
                        movie.copy(bookmark = !state.bookmark)
                    } else {
                        movie
                    }
                }
            }

            is TrendingMovieItemViewState.BookmarkClicked -> {
                from.oldData.map { movie ->
                    if (movie.id == state.id) {
                        movie.copy(bookmark = !state.bookmark)
                    } else {
                        movie
                    }
                }
            }

            else -> from.oldData
        }
    }

    data class Param(
        val state: ViewState,
        val oldData: List<MovieItemDomainModel>
    )
}