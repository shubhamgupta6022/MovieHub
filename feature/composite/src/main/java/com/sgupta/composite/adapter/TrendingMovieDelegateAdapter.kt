package com.sgupta.composite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sgupta.composite.R
import com.sgupta.composite.adapter.states.TrendingMovieItemViewState
import com.sgupta.composite.databinding.TrendingMovieItemLayoutBinding
import com.sgupta.composite.model.TrendingMovieUiModel
import com.sgupta.core.delegator.DelegateAdapter
import com.sgupta.core.delegator.DelegateAdapterItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrendingMovieDelegateAdapter @Inject constructor() :
    DelegateAdapter<TrendingMovieUiModel, TrendingMovieDelegateAdapter.ViewHolder>(
        TrendingMovieUiModel::class.java
    ) {

    private val _uiStates = MutableSharedFlow<TrendingMovieItemViewState>()
    val uiStates = _uiStates.asSharedFlow()

    private val glideOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()

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
        when(payloads.firstOrNull()) {
            is TrendingMovieUiModel.ChangePayload.BookmarkStateChanged -> viewHolder.bindBookmarkState(model)
            else -> viewHolder.bind(model)
        }
    }

    inner class ViewHolder(private val binding: TrendingMovieItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: TrendingMovieUiModel) {
            with(binding) {
                movieTitle.text = model.title
                movieRating.text = model.rating
                tvMovieYear.text = model.year.toString()
                // Add image loading logic here using your preferred image loading library
                // For example, using Glide:
                 Glide.with(moviePoster.context)
                     .load(model.posterUrl)
                     .apply(glideOptions)
                     .into(moviePoster)
                bindBookmarkState(model)
            }
        }

        fun bindBookmarkState(model: TrendingMovieUiModel) {
            with(binding) {
                if (model.bookmark) {
                    btnBookmark.setImageResource(R.drawable.ic_bookmark_filled)
                } else {
                    btnBookmark.setImageResource(R.drawable.ic_bookmark_outline)
                }
                setClickListeners(model)
            }
        }

        private fun setClickListeners(model: TrendingMovieUiModel) {
            with(binding) {
                btnBookmark.setOnClickListener {
                    delegateScope.launch {
                        _uiStates.emit(TrendingMovieItemViewState.BookmarkClicked(model.id, model.bookmark))
                    }
                }
            }
        }
    }
}