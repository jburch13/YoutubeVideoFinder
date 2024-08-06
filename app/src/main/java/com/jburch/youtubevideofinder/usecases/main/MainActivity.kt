package com.jburch.youtubevideofinder.usecases.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.jburch.youtubevideofinder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // View Model
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setup()
    }

    /*fun search() {
        val query = binding.searchView.query.toString()
        if (query.isEmpty()) {
            viewModel.query(this, query)
        }
    }*/

    private fun setup() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search()
                return false
            }
        })
    }

    private fun search() {
        viewModel.query(this, binding.searchView.query.toString())
        isEditing = false
    }
}