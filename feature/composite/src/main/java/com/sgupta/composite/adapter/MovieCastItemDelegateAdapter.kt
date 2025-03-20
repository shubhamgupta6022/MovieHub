package com.sgupta.composite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sgupta.composite.databinding.MovieCastItemLayoutBinding
import com.sgupta.composite.model.MovieCastItemUiModel
import com.sgupta.core.delegator.DelegateAdapter
import com.sgupta.core.delegator.DelegateAdapterItem
import javax.inject.Inject

class MovieCastItemDelegateAdapter @Inject constructor() :
    DelegateAdapter<MovieCastItemUiModel, MovieCastItemDelegateAdapter.ViewHolder>(
        MovieCastItemUiModel::class.java
    ) {

    private val glideOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(
            MovieCastItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun bindViewHolder(
        model: MovieCastItemUiModel,
        viewHolder: ViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
        position: Int
    ) {
        viewHolder.bind(model)
    }

    inner class ViewHolder(private val binding: MovieCastItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MovieCastItemUiModel) {
            with(binding) {
                tvName.text = model.name

                // Load image with Glide
                Glide.with(ivCast.context)
                    .load(model.logoPath)
                    .apply(glideOptions)
                    .into(ivCast)
            }
        }
    }
}