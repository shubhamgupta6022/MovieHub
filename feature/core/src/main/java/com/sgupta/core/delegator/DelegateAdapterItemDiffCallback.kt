package com.sgupta.core.delegator

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

internal class DelegateAdapterItemDiffCallback : DiffUtil.ItemCallback<DelegateAdapterItem>() {
    override fun areItemsTheSame(
        oldItem: DelegateAdapterItem,
        newItem: DelegateAdapterItem,
    ): Boolean = oldItem::class == newItem::class && oldItem.id() == newItem.id()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: DelegateAdapterItem,
        newItem: DelegateAdapterItem,
    ): Boolean = oldItem.content() == newItem.content()

    override fun getChangePayload(
        oldItem: DelegateAdapterItem,
        newItem: DelegateAdapterItem,
    ): Any = oldItem.payload(newItem)
}