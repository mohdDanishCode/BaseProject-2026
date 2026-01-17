package com.omniful.designsystem.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

/**
 * Detects any gesture.
 */
fun Modifier.detectAnyGesture(ignoreConsumed: Boolean, onGesture: () -> Unit,) = pointerInput(Unit) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            val isConsumed = event.changes.any { it.isConsumed }
            if (!isConsumed || ignoreConsumed) { // Only trigger if the gesture is not already consumed
                onGesture()
            }
        }
    }
}