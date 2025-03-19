package com.sgupta.composite.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sgupta.composite.databinding.FragmentHomeBinding
import com.sgupta.composite.adapter.manager.NowPlayingMoviesAdapterManager
import com.sgupta.composite.adapter.manager.TrendingMoviesAdapterManager
import com.sgupta.composite.adapter.states.MovieListItemViewState
import com.sgupta.composite.adapter.states.TrendingMovieItemViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var nowPlayingMoviesAdapterManager: NowPlayingMoviesAdapterManager
    private val nowPlayingMoviesAdapter by lazy {
        nowPlayingMoviesAdapterManager.createCompositeAdapter()
    }
    private val nowPlayingMoviesUiStates by lazy {
        nowPlayingMoviesAdapterManager.createMergedUiStates()
    }

    @Inject
    lateinit var trendingMoviesAdapterManager: TrendingMoviesAdapterManager
    private val trendingMoviesAdapter by lazy {
        trendingMoviesAdapterManager.createCompositeAdapter()
    }
    private val trendingMoviesAdapterUiStates by lazy {
        trendingMoviesAdapterManager.createCompositeAdapterUiStates()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewStates()
        setRecyclerView()
    }

    private fun observeViewStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect { state ->
                when (state) {
                    is HomeViewState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is HomeViewState.Success -> {
                        trendingMoviesAdapter.submitList(state.trendingItems)
                        nowPlayingMoviesAdapter.submitList(state.nowPlayingItems)
                        binding.progressBar.visibility = View.GONE
                        binding.nestedScrollView.visibility = View.VISIBLE
                    }

                    is HomeViewState.Error -> {}
                    is HomeViewState.NowPlayingItemsUpdated -> {
                        nowPlayingMoviesAdapter.submitList(state.list)
                    }
                    is HomeViewState.TrendingMovieItemsUpdated -> {
                        trendingMoviesAdapter.submitList(state.list)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            nowPlayingMoviesUiStates.collect { state ->
                when (state) {
                    is MovieListItemViewState.BookmarkClicked -> {
                        viewModel.updateState(state)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            trendingMoviesAdapterUiStates.collect { state ->
                when (state) {
                    is TrendingMovieItemViewState.BookmarkClicked -> {
                        viewModel.updateState(state)
                    }
                }
            }
        }
    }

    private fun setRecyclerView() {
        binding.rvTrendingMovies.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingMoviesAdapter
            itemAnimator = null
        }
        binding.rvNowPlaying.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = nowPlayingMoviesAdapter
            itemAnimator = null
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}