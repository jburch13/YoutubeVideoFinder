package com.jburch.youtubevideofinder.model.domain

import com.google.gson.annotations.SerializedName

data class YoutubeSearchListResponse(val items: List<YoutubeVideo>? = null)

data class YoutubeVideo(
    @SerializedName("snippet") val video: YoutubeVideoInfo? = null
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
