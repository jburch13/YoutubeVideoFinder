package com.jburch.youtubevideofinder.usecases.video

import android.content.Context
import android.content.Intent
import com.jburch.youtubevideofinder.model.domain.YoutubeVideo
import com.jburch.youtubevideofinder.usecases.base.BaseActivityRouter

class VideoRouter: BaseActivityRouter {

    companion object {

        private const val YOUTUBE_VIDEO = "YOUTUBE_VIDEO"

        fun video(intent: Intent) : YoutubeVideo? {
            intent.getStringExtra(YOUTUBE_VIDEO)?.let {  videoJSON ->
                return YoutubeVideo.fromJson(videoJSON)
            }
            return null
        }
    }

    override fun intent(activity: Context): Intent = Intent(activity, VideoActivity::class.java)

    fun intent(activity: Context, video: YoutubeVideo): Intent {
        val intent = intent(activity)
        intent.putExtra(YOUTUBE_VIDEO, YoutubeVideo.toJson(video))
        return intent
    }

    fun launch(activity: Context, video: YoutubeVideo) {
        activity.startActivity(intent(activity, video))
    }

}