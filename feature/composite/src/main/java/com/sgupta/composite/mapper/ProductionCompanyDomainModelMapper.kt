package com.sgupta.composite.mapper

import com.sgupta.composite.model.MovieCastItemUiModel
import com.sgupta.core.mapper.Mapper
import com.sgupta.domain.model.ProductionCompanyDomainModel
import javax.inject.Inject

class ProductionCompanyDomainModelMapper @Inject constructor() :
    Mapper<ProductionCompanyDomainModel, MovieCastItemUiModel> {
    override fun convert(from: ProductionCompanyDomainModel): MovieCastItemUiModel {
        return MovieCastItemUiModel(
            id = from.id,
            name = from.name,
            logoPath = from.logoUrl
        )
    }
}