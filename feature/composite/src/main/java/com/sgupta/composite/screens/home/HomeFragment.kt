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
import com.sgupta.composite.manager.NowPlayingMoviesAdapterManager
import com.sgupta.composite.manager.TrendingMoviesAdapterManager
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

    @Inject
    lateinit var trendingMoviesAdapterManager: TrendingMoviesAdapterManager
    private val trendingMoviesAdapter by lazy {
        trendingMoviesAdapterManager.createCompositeAdapter()
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