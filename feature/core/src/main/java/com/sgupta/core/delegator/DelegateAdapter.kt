package com.sgupta.core.delegator

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class DelegateAdapter<M, in VH : RecyclerView.ViewHolder>(val modelClass: Class<out M>) {
    protected val delegateScope by lazy { CoroutineScope(Dispatchers.Main + SupervisorJob()) }

    abstract fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    abstract fun bindViewHolder(
        model: M,
        viewHolder: VH,
        payloads: List<DelegateAdapterItem.Payloadable>,
        position: Int,
    )

    open fun onViewRecycled(viewHolder: VH) = Unit

    open fun onViewDetachedFromWindow(viewHolder: VH) = Unit

    open fun onViewAttachedToWindow(viewHolder: VH) = Unit
}