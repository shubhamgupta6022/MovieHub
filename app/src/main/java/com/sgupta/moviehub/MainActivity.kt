package com.sgupta.moviehub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sgupta.composite.home.HomeFragment
import com.sgupta.moviehub.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setHomeFragment()
    }

    private fun setHomeFragment() {
        val fragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame_layout, fragment)
            .commit()
    }
}