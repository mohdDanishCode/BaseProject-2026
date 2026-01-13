package com.omniful.designsystem.components.videoPlayer

import androidx.media3.common.Player

data class VideoUiState(
    val player: Player? = null,
    val videos: List<Component> = emptyList(),
) {

    fun playMediaAt(position: Int, playWhenReady: Boolean = false) {
        player?.let { player ->
            if (player.currentMediaItemIndex == position && player.isPlaying) {
                return
            }

            player.seekToDefaultPosition(position)
            player.playWhenReady = playWhenReady
            player.prepare()
        }
    }
}
