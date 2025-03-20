package com.sgupta.composite.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sgupta.composite.adapter.manager.NowPlayingMoviesAdapterManager
import com.sgupta.composite.adapter.manager.SearchMoviesAdapterManager
import com.sgupta.composite.adapter.states.SearchListItemViewState
import com.sgupta.composite.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchFragmentViewModel by viewModels()

    @Inject
    lateinit var moviesAdapterManager: SearchMoviesAdapterManager
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
        binding = FragmentSearchBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiStates()
        initViews()
    }

    private fun initViews() {
        binding.etSearch.addTextChangedListener { editable ->
            viewModel.onSearchQueryChanged(editable.toString())
        }
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = moviesAdapter
        }
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeUiStates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect {
                    when (it) {
                        is SearchViewState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.tvNoData.visibility = View.GONE
                        }

                        is SearchViewState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvNoData.visibility = View.GONE
                        }

                        is SearchViewState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvNoData.visibility = View.GONE
                            moviesAdapter.submitList(it.items)
                        }

                        SearchViewState.NoData -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvNoData.visibility = View.VISIBLE
                            moviesAdapter.submitList(emptyList())
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            moviesUiStates.collect {
                when(it) {
                    is SearchListItemViewState.ItemClicked -> {
                        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(it.movieId)
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }
}