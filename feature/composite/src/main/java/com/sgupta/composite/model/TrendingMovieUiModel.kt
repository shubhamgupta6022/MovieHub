package com.sgupta.composite.model

import com.sgupta.core.delegator.DelegateAdapterItem

data class TrendingMovieUiModel(
    val id: Int,
    val title: String,
    val rating: String,
    val year: Int,
    val posterUrl: String
) : DelegateAdapterItem {
    override fun id(): Any = id
    override fun content(): Any = this
}