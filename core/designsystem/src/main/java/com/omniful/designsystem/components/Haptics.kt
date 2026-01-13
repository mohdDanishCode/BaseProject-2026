package com.omniful.designsystem.components

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

object Haptics {

    private fun getVibrator(context: Context): Vibrator {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Application.VIBRATOR_SERVICE) as Vibrator
        }
    }

    fun setDoubleHapticEffect(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createWaveform(longArrayOf(0, 30, 100, 30), -1)
            val vibrator = getVibrator(context)
            vibrator.cancel()
            vibrator.vibrate(effect)
        }
    }

    fun setTapHapticEffect(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
    }
}

fun Modifier.onClickWithHaptics(enable:Boolean=true,onClick: () -> Unit): Modifier = composed {
    val haptic = LocalHapticFeedback.current
    val view = LocalView.current
    val interactionSource = remember { MutableInteractionSource() }
    this.clickable(
        enabled = enable,
//        interactionSource = interactionSource,
//        indication = rememberRipple(bounded = true, color = B4),
        onClick = {
            Haptics.setTapHapticEffect(view)
            onClick()
        },
    )
//        .onTouchWithCustomRippleAndHaptics {  }
}

fun Modifier.onTapWithHaptics(onClick: () -> Unit): Modifier = composed {
    val view = LocalView.current
    this.pointerInput(Unit) {
        detectTapGestures {
            Haptics.setTapHapticEffect(view)
            onClick()
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.onTouchWithCustomRippleAndHaptics(onClick: () -> Unit): Modifier = composed {
    var rippleAlpha by remember { mutableStateOf(0f) }
    var rippleScale by remember { mutableStateOf(1f) }
    val view = LocalView.current
    val rippleScaleAnim = animateFloatAsState(
        targetValue = if (rippleAlpha > 0f) 1.5f else 1f,
        animationSpec = tween(300)
    )
    val coroutineScope= rememberCoroutineScope()
    this.pointerInput(Unit) {
        detectTapGestures(
            onPress = {
                coroutineScope {
                    launch {
                        rippleAlpha = 0.5f
                        rippleScale = rippleScaleAnim.value
                    }

                    try {
                        Haptics.setTapHapticEffect(view)
                        onClick()
                    } finally {
                        launch {
                            rippleAlpha = 0f
                            rippleScale = 1f
                        }
                    }
                }
            }
        )
    }.drawWithContent {
        drawContent()
        val center = Offset(size.width / 2, size.height / 2)
        scale(scale = rippleScale, pivot = center) {
            drawCircle(
                color = Color.Gray.copy(alpha = rippleAlpha),
                radius = size.minDimension / 2,
                center = center
            )
        }
    }


}

fun Modifier.onClickWithLongHaptics(onClick: () -> Unit): Modifier = composed {
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }

    this.clickable(
//        interactionSource = interactionSource,
//        indication = rememberRipple(bounded = false, color = Blank),
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
    )
}

fun Modifier.onClickWithDoubleHapticEffect(onClick: () -> Unit): Modifier = composed {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    this.clickable(
//        interactionSource = interactionSource,
//        indication = rememberRipple(bounded = false, color = Blank),
        onClick = {
            Haptics.setDoubleHapticEffect(context)
            onClick()
        },
    )
}