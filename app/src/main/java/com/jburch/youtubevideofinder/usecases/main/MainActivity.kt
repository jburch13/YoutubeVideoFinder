package com.jburch.youtubevideofinder.usecases.main

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jburch.youtubevideofinder.databinding.ActivityMainBinding
import com.jburch.youtubevideofinder.usecases.common.rows.SearchRecyclerViewAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // View Model
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setup()
        data()
    }


    private fun setup() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                search()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.btnRetrySearch.setOnClickListener {
            if (viewModel.lastSearchQ.isNotEmpty()) {
                search(true)
            }
        }

        this.let { context ->
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = SearchRecyclerViewAdapter(context, arrayListOf())
        }

        if (VERSION.SDK_INT >= VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun data() {
        searching(false)
        viewModel.loading.observe(this) {
            if (it) {
                searching(true)
            } else {
                searching(false)
                showVideos()
            }
        }
    }

    private fun search(retry: Boolean = false) {
        binding.searchView.clearFocus()
        val query = if (retry) viewModel.lastSearchQ else binding.searchView.query.toString()
        viewModel.query(this, query)
        isEditing = false
    }

    private fun showVideos() {

        if (viewModel.genericError) {
            showError()
        } else {
            if (viewModel.videos.isEmpty() && !isEditing) {
                // No hi han videos
                binding.recyclerView.visibility = View.GONE
                binding.tvNoResults.visibility = View.VISIBLE
                Toast.makeText(this, "No hi han videos a mostrar", Toast.LENGTH_LONG).show()
            } else {
                binding.tvNoResults.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                val adapter = binding.recyclerView.adapter as SearchRecyclerViewAdapter
                adapter.videos = viewModel.videos
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun searching(show: Boolean) = if (show) {
        binding.pbLoading.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.INVISIBLE
        binding.tvNoResults.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
    } else {
        binding.pbLoading.visibility = View.GONE
    }

    private fun showError() {
        binding.pbLoading.visibility = View.GONE
        binding.tvNoResults.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.errorContainer.visibility = View.VISIBLE

    }
}