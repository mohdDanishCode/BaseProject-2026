package com.omniful.app.presentation.video

import android.app.Application
import androidx.lifecycle.ViewModel
import com.omniful.designsystem.components.videoPlayer.VideoPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    application: Application,
) :  ViewModel() {
    private val videoPlayerManagerMap: HashMap<String, VideoPlayerManager> = hashMapOf()

    fun getVideoPlayerManagerById(id: String): VideoPlayerManager {
        if (!videoPlayerManagerMap.contains(id)) {
            videoPlayerManagerMap[id] = VideoPlayerManager()
        }
        return videoPlayerManagerMap[id]!!
    }

    override fun onCleared() {
        clear()
        super.onCleared()
    }

    fun clear() {
        CoroutineScope(Dispatchers.IO).launch {
            videoPlayerManagerMap.forEach {
                it.value.clearState() {
                }
            }
            videoPlayerManagerMap.clear()
        }
    }
}