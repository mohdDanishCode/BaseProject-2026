package com.omniful.designsystem.components.videoPlayer

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import com.omniful.designsystem.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

data class Component(
    val id: String = UUID.randomUUID().toString(),
    val type: ElementType?,
    val value: ComponentValue
)

enum class ElementType(val value: String) {
    IMAGE_WITH_TITLE("image_with_title"),
    IMAGE("image"),
    VIDEO("video"),
    EXTERNAL_VIDEO("external_video"),
    PRODUCT("product")
}

// Component Value
data class ComponentValue(
    val title: String? = null,
    val imageSrc: String? = null,
    val action: String? = null,
     val height: Double? = null,
    val width: Double? = null,
    val videoSrc: String? = null,
     val handle: String? = null
)

class VideoPlayerManager {

    private val _state = MutableStateFlow(VideoUiState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<VideoEffect>()
    val effect = _effect.asSharedFlow()

    var setComponentJob: Job? = null
    var clearStateJob: Job? = null
    var playerErrorJob: Job? = null
    var tapJob: Job? = null

    fun setComponent(componentList: List<Component>) {
        setComponentJob = CoroutineScope(Dispatchers.IO).launch() {
            _state.emit(
                VideoUiState(
                    videos = componentList,
                ),
            )
        }
    }

    fun clearState(done: () -> Unit) {
        clearStateJob = CoroutineScope(Dispatchers.IO).launch() {
            withContext(Dispatchers.Main) {
                releasePlayer(false)
            }
            _state.emit(VideoUiState())
            _effect.emit(ResetAnimationEffect)
            setComponentJob?.cancel()
            playerErrorJob?.cancel()
            tapJob?.cancel()
            clearStateJob?.cancel()
            done()
        }
    }

    fun createPlayer(context: Context) {
        _state.update { state ->
            if (state.player == null) {
                state.copy(
//                    player = ExoPlayer.Builder(context).build().apply {
//                        repeatMode = Player.REPEAT_MODE_ONE
//                        setMediaItems(state.videos.toMediaItems())
//                        prepare()
//                    },
                    player = ExoPlayer.Builder(context).setRenderersFactory(
                        DefaultRenderersFactory(context).setEnableDecoderFallback(true),
                    )
                        .build().apply {
                            repeatMode = Player.REPEAT_MODE_OFF
                            setMediaItems(state.videos.toMediaItems())
                            prepare()
                        },
                )
            } else {
                state
            }
        }
    }

    fun releasePlayer(isChangingConfigurations: Boolean) {
        if (isChangingConfigurations) {
            return
        }
        _state.update { state ->
            state.player?.release()
            state.copy(player = null)
        }
    }

    private fun List<Component>.toMediaItems(): List<MediaItem> {
        return map {
            MediaItem.fromUri(it.value?.videoSrc ?: it.value?.imageSrc ?: "")
        }
    }

    fun onPlayerError() {
        playerErrorJob = CoroutineScope(Dispatchers.Main).launch() {
            state.value.player?.let { player ->
                _effect.emit(
                    PlayerErrorEffect(
                        message = player.playerError?.message.toString(),
                        code = player.playerError?.errorCode ?: -1,
                    ),
                )
            }
        }
    }

    fun onTappedScreen() {
        tapJob = CoroutineScope(Dispatchers.Main).launch() {
            _effect.emit(ResetAnimationEffect)
            state.value.player?.let { player ->
                val drawable = if (player.isPlaying) {
                    player.pause()
                    R.drawable.pause_icon
                } else {
                    player.play()
                    R.drawable.play_icon
                }
                _effect.emit(AnimationEffect(drawable))
            }
        }
    }
}
