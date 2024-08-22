package com.jburch.youtubevideofinder.model.domain

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

data class YoutubeSearchListResponse(val items: List<YoutubeVideo>? = null)

data class YoutubeVideo(
    @SerializedName("id") val id: VideoId? = null,
    @SerializedName("snippet") val video: YoutubeVideoInfo? = null
) {

    // Gson

    companion object {
        fun toJson(video: YoutubeVideo): String {
            return GsonBuilder().create().toJson(video)
        }

        fun fromJson(json: String): YoutubeVideo {
            return GsonBuilder().create().fromJson(json, YoutubeVideo::class.java)
        }
    }

}

data class VideoId(
    @SerializedName("videoId") val videoId: String? = null  // Represents the videoId string
)

data class YoutubeVideoInfo(
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("thumbnails") val thumbnails: YoutubeVideoThumbnails? = null,
)

data class YoutubeVideoThumbnails(
    @SerializedName("default") val default: YoutubeVideoThumbnail? = null,
    @SerializedName("medium") val medium: YoutubeVideoThumbnail? = null,
    @SerializedName("high") val high: YoutubeVideoThumbnail? = null
)
data class YoutubeVideoThumbnail(
    @SerializedName("url") val url: String? = null,
    @SerializedName("width") val width: Int? = null,
    @SerializedName("height") val height: Int? = null,
)
