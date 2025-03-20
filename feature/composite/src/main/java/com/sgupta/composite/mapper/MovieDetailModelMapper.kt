package com.sgupta.composite.mapper

import com.sgupta.composite.model.MovieDetailUiModel
import com.sgupta.core.mapper.Mapper
import com.sgupta.domain.model.MovieDetailDomainModel
import javax.inject.Inject

class MovieDetailModelMapper @Inject constructor(
    private val productionCompanyDomainModelMapper: ProductionCompanyDomainModelMapper
) : Mapper<MovieDetailDomainModel, MovieDetailUiModel> {
    override fun convert(from: MovieDetailDomainModel): MovieDetailUiModel {
        return MovieDetailUiModel(
            title = from.title,
            backdropUrl = from.backdropUrl,
            postUrl = from.posterUrl,
            overview = from.overview,
            cast = from.productionCompanies.map {
                productionCompanyDomainModelMapper.convert(it)
            }
        )
    }
}

