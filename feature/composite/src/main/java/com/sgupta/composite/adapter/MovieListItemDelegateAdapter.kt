package com.sgupta.composite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sgupta.composite.databinding.MovieListItemLayoutBinding
import com.sgupta.composite.model.MovieListItemUiModel
import com.sgupta.core.delegator.DelegateAdapter
import com.sgupta.core.delegator.DelegateAdapterItem
import javax.inject.Inject

class MovieListItemDelegateAdapter @Inject constructor() :
    DelegateAdapter<MovieListItemUiModel, MovieListItemDelegateAdapter.ViewHolder>(
        MovieListItemUiModel::class.java
    ) {

    private val glideOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(
            MovieListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun bindViewHolder(
        model: MovieListItemUiModel,
        viewHolder: ViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
        position: Int
    ) {
        viewHolder.bind(model)
    }

    inner class ViewHolder(private val binding: MovieListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MovieListItemUiModel) {
            with(binding) {
                tvTitle.text = model.title
                tvRating.text = model.rating
                tvYear.text = model.year.toString()

                // Load image with Glide
                Glide.with(ivPoster.context)
                    .load(model.posterUrl)
                    .apply(glideOptions)
                    .into(ivPoster)

            }
        }
    }
}