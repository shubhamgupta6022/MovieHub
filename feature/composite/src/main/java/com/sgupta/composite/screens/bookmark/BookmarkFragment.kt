package com.sgupta.composite.screens.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sgupta.composite.adapter.manager.NowPlayingMoviesAdapterManager
import com.sgupta.composite.adapter.states.MovieListItemViewState
import com.sgupta.composite.databinding.FragmentBookmarkBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private val viewModel: BookmarkFragmentViewModel by viewModels()

    @Inject
    lateinit var moviesAdapterManager: NowPlayingMoviesAdapterManager
    private val moviesAdapter by lazy {
        moviesAdapterManager.createCompositeAdapter()
    }
    private val moviesUiStates by lazy {
        moviesAdapterManager.createMergedUiStates()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiStates()
        initViews()
    }

    private fun initViews() {
        setUpRecyclerView()
        binding.ivArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeUiStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            moviesUiStates.collect {
                when (it) {
                    is MovieListItemViewState.BookmarkClicked -> {
                        viewModel.updateState(it)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect {
                when (it) {
                    is BookmarkViewState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    BookmarkViewState.NoData -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvNoData.visibility = View.VISIBLE
                        binding.rvNowPlaying.visibility = View.GONE
                    }

                    is BookmarkViewState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        moviesAdapter.submitList(it.items)
                        binding.rvNowPlaying.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvNowPlaying.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = moviesAdapter
        }
    }

    companion object {
        fun newInstance() = BookmarkFragment()
    }
}