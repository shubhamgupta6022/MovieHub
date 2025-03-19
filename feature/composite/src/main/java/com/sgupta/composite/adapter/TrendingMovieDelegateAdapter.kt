package com.sgupta.composite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sgupta.composite.databinding.TrendingMovieItemLayoutBinding
import com.sgupta.composite.model.TrendingMovieUiModel
import com.sgupta.core.delegator.DelegateAdapter
import com.sgupta.core.delegator.DelegateAdapterItem
import javax.inject.Inject

class TrendingMovieDelegateAdapter @Inject constructor() :
    DelegateAdapter<TrendingMovieUiModel, TrendingMovieDelegateAdapter.ViewHolder>(
        TrendingMovieUiModel::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = TrendingMovieItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun bindViewHolder(
        model: TrendingMovieUiModel,
        viewHolder: ViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
        position: Int
    ) {
        viewHolder.bind(model)
    }

    inner class ViewHolder(private val binding: TrendingMovieItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: TrendingMovieUiModel) {
            with(binding) {
                movieTitle.text = model.title
                movieRating.text = model.rating.toString()
                tvMovieYear.text = model.year.toString()
                // Add image loading logic here using your preferred image loading library
                // For example, using Glide:
                // Glide.with(moviePoster)
                //     .load(model.posterUrl)
                //     .into(moviePoster)
            }
        }
    }
}