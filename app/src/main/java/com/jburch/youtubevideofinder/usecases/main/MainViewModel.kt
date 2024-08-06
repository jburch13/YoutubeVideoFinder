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

    fun load() {
        loading.postValue(false)
    }

    fun query(context: Context, query: String) {

        if (query.isNotEmpty()) {

            loading.postValue(true)
            YoutubeService.search(context, query, { youtubeVideos ->
                videos = youtubeVideos
                load()
            }, {
                load()
                // TODO: Carregar de la BBDD
                Log.i("Failure", "Error buscant videos")
            })
        } else {
            // TODO: Carregar de la Session
        }
    }

}