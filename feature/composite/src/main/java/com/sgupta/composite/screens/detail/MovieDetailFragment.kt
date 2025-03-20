package com.sgupta.composite.screens.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sgupta.composite.R
import com.sgupta.composite.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_MOVIE_ID = "movieId"

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var movieId: Int? = null
    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(ARG_MOVIE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    companion object {
        fun newInstance(movieId: Int) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_MOVIE_ID, movieId)
                }
            }
    }
}