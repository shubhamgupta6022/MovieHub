package com.sgupta.composite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sgupta.composite.R
import com.sgupta.composite.adapter.states.MovieListItemViewState
import com.sgupta.composite.adapter.states.SearchListItemViewState
import com.sgupta.composite.databinding.MovieListItemLayoutBinding
import com.sgupta.composite.databinding.MovieSearchItemLayoutBinding
import com.sgupta.composite.model.MovieListItemUiModel
import com.sgupta.core.delegator.DelegateAdapter
import com.sgupta.core.delegator.DelegateAdapterItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieSearchItemDelegateAdapter @Inject constructor() :
    DelegateAdapter<MovieListItemUiModel, MovieSearchItemDelegateAdapter.ViewHolder>(
        MovieListItemUiModel::class.java
    ) {

    private val _uiStates = MutableSharedFlow<SearchListItemViewState>()
    val uiStates = _uiStates.asSharedFlow()

    private val glideOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(
            MovieSearchItemLayoutBinding.inflate(
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

    inner class ViewHolder(private val binding: MovieSearchItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MovieListItemUiModel) {
            with(binding) {
                tvTitle.text = model.title
                tvGenres.text = model.overview

                // Load image with Glide
                Glide.with(ivPoster.context)
                    .load(model.posterUrl)
                    .apply(glideOptions)
                    .into(ivPoster)

                root.setOnClickListener {
                    delegateScope.launch {
                        _uiStates.emit(SearchListItemViewState.ItemClicked(movieId = model.id))
                    }
                }
            }
        }

    }
}