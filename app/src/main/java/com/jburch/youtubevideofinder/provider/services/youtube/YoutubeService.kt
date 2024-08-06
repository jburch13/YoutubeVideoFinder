package com.jburch.youtubevideofinder.provider.services.youtube

import android.content.Context
import com.jburch.youtubevideofinder.R
import com.jburch.youtubevideofinder.core.RetrofitHelper
import com.jburch.youtubevideofinder.model.domain.YoutubeSearchListResponse
import com.jburch.youtubevideofinder.model.domain.YoutubeVideo
import com.jburch.youtubevideofinder.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

object YoutubeService {

    private enum class YoutubeServiceAPI {

        SEARCH;

        fun baseUrl(): String {

            return when (this) {
                SEARCH -> Constants.YOUTUBE_SEARCH_URI
            }
        }
    }


    private interface YoutubeAPIService {

        @GET("search")
        fun search(
            @Query("part") part: String = "snippet",
            @Query("maxResults") maxResults: Int = 10,
            @Query("q") q: String,
            @Query("key") apiKey: String
        ): Call<YoutubeSearchListResponse>
    }

    fun search(context: Context, query: String, success: (items: List<YoutubeVideo>) -> Unit, failure: () -> Unit, retry: Boolean = false) {
        val retrofit = RetrofitHelper.getRetrofit(YoutubeServiceAPI.SEARCH.baseUrl())
        val service = retrofit.create(YoutubeAPIService::class.java)
        val apiKey = context.getString(R.string.youtube_api_key)
        val headers: Map<String, String> = mapOf("Authorization" to "Bearer ", "Accept" to "application/json")
        service.search(q = query.filter { !it.isWhitespace() }, apiKey = apiKey).enqueue(object : Callback<YoutubeSearchListResponse> {

            override fun onResponse(
                call: Call<YoutubeSearchListResponse>,
                response: Response<YoutubeSearchListResponse>
            ) {
                val videos = response.body()?.items?.toMutableList()
                if (videos?.isNotEmpty() == true) {
                    success(videos)
                } else {
                    // TODO: Tractament d'errors
                }
            }

            override fun onFailure(call: Call<YoutubeSearchListResponse>, t: Throwable) {
                // Is done in onResponse
            }

        })
    }
}