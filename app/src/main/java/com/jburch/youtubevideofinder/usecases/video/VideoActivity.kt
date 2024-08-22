package com.jburch.youtubevideofinder.usecases.video

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jburch.youtubevideofinder.databinding.ActivityVideoBinding
import com.jburch.youtubevideofinder.model.domain.YoutubeVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

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
        viewModel = ViewModelProvider(this)[VideoViewModel::class.java]

        setup()
    }

    private fun setup() {

        lifecycle.addObserver(binding.ytPlayerView)

        val ytPlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                video?.id?.videoId?.let { videoId ->
                    youTubePlayer.loadVideo(videoId, 0F)
                } ?: run {
                    Log.e("VideoActivity", "Video ID is null, cannot load the video")
                }
            }
        }

        binding.ytPlayerView.addYouTubePlayerListener(ytPlayerListener)
    }
}