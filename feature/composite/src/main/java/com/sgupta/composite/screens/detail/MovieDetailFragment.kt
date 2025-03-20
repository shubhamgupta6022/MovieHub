package com.sgupta.composite.screens.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sgupta.composite.adapter.manager.MovieCastAdapterManager
import com.sgupta.composite.databinding.FragmentMovieDetailBinding
import com.sgupta.composite.model.MovieCastItemUiModel
import com.sgupta.composite.model.MovieDetailUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    private val glideOptions by lazy {
        RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
    }

    @Inject
    lateinit var adapterManager: MovieCastAdapterManager
    private val movieCastAdapter by lazy { adapterManager.createCompositeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = args.movieId
        viewModel.getMovieDetail(movieId)

        setupObservers()
        initViews()
    }

    private fun initViews() {
        with(binding) {
            ivArrowBack.setOnClickListener {
                findNavController().popBackStack()
            }
            rvCast.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = movieCastAdapter
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    when (state) {
                        is DetailViewState.Loading -> {
                        }

                        is DetailViewState.Success -> {
                            setDetailView(state.model)
                        }

                        is DetailViewState.Error -> {
                        }
                    }
                }
            }
        }
    }

    private fun setDetailView(model: MovieDetailUiModel) {
        with(binding) {
            tvTitle.text = model.title
            tvOverviewDesc.text = model.overview

            movieCastAdapter.submitList(model.cast)

            Glide.with(ivBanner.context)
                .load(model.backdropUrl)
                .apply(glideOptions)
                .into(ivBanner)

            Glide.with(ivPoster.context)
                .load(model.postUrl)
                .apply(glideOptions)
                .into(ivPoster)
        }
    }
}