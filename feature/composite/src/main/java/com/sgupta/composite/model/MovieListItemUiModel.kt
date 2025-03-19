package com.sgupta.composite.model

import com.sgupta.core.delegator.DelegateAdapterItem

data class MovieListItemUiModel(
    val id: Int,
    val title: String,
    val rating: String,
    val year: Int,
    val posterUrl: String
) : DelegateAdapterItem {
    override fun id(): Any = id

    override fun content(): Any = Content(id)

    inner class Content(val id: Int) {
        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        override fun hashCode(): Int {
            return super.hashCode()
        }
    }
}