package com.jburch.youtubevideofinder.usecases.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jburch.youtubevideofinder.model.domain.YoutubeVideo
import com.jburch.youtubevideofinder.provider.services.youtube.YoutubeService

class MainViewModel: ViewModel() {

    val loading: MutableLiveData<Boolean> = MutableLiveData()

    var videos: List<YoutubeVideo> = arrayListOf()
        private set

    var genericError: Boolean = false
        private set

    var lastSearchQ: String = ""
        private set

    fun load() {
        loading.postValue(false)
    }

    fun query(context: Context, query: String) {
        lastSearchQ = query
        if (query.isNotEmpty()) {
            genericError = false
            loading.postValue(true)
            YoutubeService.search(context, query, { youtubeVideos ->
                videos = youtubeVideos
                load()
            }, {
                onSearchError()
            })
        } else {
            onSearchError()
        }
    }

    private fun onSearchError() {
        videos = arrayListOf()
        genericError = true
        load()
    }

}