package com.sgupta.composite.model

import com.sgupta.core.delegator.DelegateAdapterItem
import java.util.Objects

data class MovieCastItemUiModel(
    val id: Int,
    val logoPath: String,
    val name: String,
) : DelegateAdapterItem {
    override fun id(): Any = id

    override fun content(): Any = Content(id)

    inner class Content(val id: Int) {
        override fun equals(other: Any?): Boolean {
            if (other is Content) {
                return id == other.id
            }
            return super.equals(other)
        }

        override fun hashCode(): Int {
            return 32 * Objects.hashCode(id)
        }
    }

}