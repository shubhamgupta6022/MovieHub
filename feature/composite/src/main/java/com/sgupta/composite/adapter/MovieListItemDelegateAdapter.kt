package com.sgupta.composite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sgupta.composite.databinding.MovieListItemLayoutBinding
import com.sgupta.composite.model.MovieListItemUiModel
import com.sgupta.core.delegator.DelegateAdapter
import com.sgupta.core.delegator.DelegateAdapterItem
import javax.inject.Inject

class MovieListItemDelegateAdapter @Inject constructor() :
    DelegateAdapter<MovieListItemUiModel, MovieListItemDelegateAdapter.ViewHolder>(
        MovieListItemUiModel::class.java
    ) {
    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding =
            MovieListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun bindViewHolder(
        model: MovieListItemUiModel,
        viewHolder: MovieListItemDelegateAdapter.ViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
        position: Int
    ) {
        viewHolder.bind()
    }

    inner class ViewHolder(private val binding: MovieListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            with(binding) {

            }
        }
    }
}