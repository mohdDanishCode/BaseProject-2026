package com.omniful.designsystem.components.videoPlayer

import androidx.annotation.DrawableRes

sealed class VideoEffect

data class PlayerErrorEffect(val message: String, val code: Int) : VideoEffect()

data class AnimationEffect(@DrawableRes val drawable: Int) : VideoEffect()

data object ResetAnimationEffect : VideoEffect()
