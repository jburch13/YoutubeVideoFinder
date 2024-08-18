package com.jburch.youtubevideofinder.usecases.common.rows

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jburch.youtubevideofinder.R
import com.jburch.youtubevideofinder.databinding.ViewVideoItemBinding
import com.jburch.youtubevideofinder.model.domain.YoutubeVideo
import com.jburch.youtubevideofinder.model.domain.YoutubeVideoInfo
import com.jburch.youtubevideofinder.usecases.video.VideoRouter

class SearchRecyclerViewAdapter(val context: Context, var videos: List<YoutubeVideo>):
    RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ViewVideoItemBinding.bind(itemView)

        fun bind(video: YoutubeVideo) = with(itemView) {

            val videoInfo: YoutubeVideoInfo? = video.video

            // Title
            if (videoInfo != null) {
                val videoPreview = videoInfo.thumbnails?.default?.url ?: "https://icons.veryicon.com/png/o/commerce-shopping/mobile-taobao-icon-library/video-86.png"
                // Preview
                Glide.with(context).load(videoPreview).into(binding.imgPreview)

                // Title
                binding.tvTitle.text = videoInfo.title ?: context.getString(R.string.youtube_video_no_title)

                // Description
                binding.tvDescription.text = videoInfo.description ?: context.getString(R.string.youtube_video_no_description)
            }

            itemView.setOnClickListener {
                VideoRouter().launch(context, video)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_video_item, parent, false)
        val viewHolder = ViewHolder(view)

        val binding = viewHolder.binding

        return viewHolder
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videos[position])
    }
}