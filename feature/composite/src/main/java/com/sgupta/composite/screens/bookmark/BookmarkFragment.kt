package com.sgupta.composite.screens.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sgupta.composite.R
import com.sgupta.composite.databinding.FragmentBookmarkBinding
import com.sgupta.composite.databinding.FragmentHomeBinding

class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = BookmarkFragment()
    }
}