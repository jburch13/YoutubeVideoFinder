package com.jburch.youtubevideofinder.usecases.video

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jburch.youtubevideofinder.databinding.ActivityVideoBinding
import com.jburch.youtubevideofinder.model.domain.YoutubeVideo

class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding

    private var video: YoutubeVideo? = null

    private lateinit var viewModel: VideoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)

        // Content
        setContentView(binding.root)

        // Extra
        video = VideoRouter.video(intent)

        // View Model
        viewModel = ViewModelProvider(this).get(VideoViewModel::class.java)
    }
}