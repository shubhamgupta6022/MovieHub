package com.sgupta.composite.model

import com.sgupta.core.delegator.DelegateAdapterItem
import java.util.Objects

data class TrendingMovieUiModel(
    val id: Int,
    val title: String,
    val rating: String,
    val year: Int,
    val posterUrl: String,
    val bookmark: Boolean
) : DelegateAdapterItem {
    override fun id(): Any = id

    override fun content(): Any = Content(id, bookmark)

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is TrendingMovieUiModel) {
            return when {
                other.bookmark != bookmark -> ChangePayload.BookmarkStateChanged
                else -> DelegateAdapterItem.Payloadable.None
            }
        }
        return super.payload(other)
    }

    inner class Content(val id: Int, val bookmark: Boolean) {
        override fun equals(other: Any?): Boolean {
            if (other is Content) {
                return bookmark == other.bookmark
            }
            return super.equals(other)
        }

        override fun hashCode(): Int {
            return 32 * Objects.hashCode(bookmark)
        }
    }

    sealed class ChangePayload : DelegateAdapterItem.Payloadable {
        data object BookmarkStateChanged : ChangePayload()
    }
}