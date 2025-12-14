package com.omniful.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class InputSize {
    L, M, S
}

enum class InputState {
    Default,
    Filled,
    Success,
    Failed,
    Warning
}

@Composable
fun OMFTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    label: String? = null,
    caption: String? = null,
    size: InputSize = InputSize.M,
    state: InputState = InputState.Default,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leftIcon: (@Composable ((Dp) -> Unit))? = null,
    rightIcon: (@Composable ((Dp) -> Unit))? = null
) {
    val colors = LocalOMFColors.current
    val spacing = LocalOMFSize.current.spacing
    val radius = LocalOMFSize.current.radius
    val typography = LocalTypography.current

    // --- size mapping ---
    val height: Dp
    val horizontalPadding: Dp
    val cornerRadius: Dp
    val textStyle: TextStyle
    val iconSize: Dp

    when (size) {
        InputSize.L -> {
            height = 48.dp
            horizontalPadding = spacing.spacing3
            cornerRadius = radius.radius3
            textStyle = typography.body.styles[BodyType.B01]!!.medium
            iconSize = 24.dp
        }
        InputSize.M -> {
            height = 40.dp
            horizontalPadding = spacing.spacing3
            cornerRadius = radius.radius3
            textStyle = typography.body.styles[BodyType.B02]!!.medium
            iconSize = 16.dp
        }
        InputSize.S -> {
            height = 32.dp
            horizontalPadding = spacing.spacing2
            cornerRadius = radius.radius3
            textStyle = typography.caption.styles[CaptionType.C01]!!.medium
            iconSize = 16.dp
        }
    }

    // --- state colors ---
    val borderColor = when (state) {
        InputState.Default -> Color.LightGray
        InputState.Filled -> colors.primary
        InputState.Success -> Color(0xFF4CAF50)
        InputState.Warning -> Color(0xFFFF9800)
        InputState.Failed -> Color(0xFFF44336)
    }

    val backgroundColor = when (state) {
        InputState.Default,InputState.Filled -> Color.White
        else -> {
            borderColor.copy(alpha = (0.05f))
        }
    }

    Column(modifier = modifier) {

        // Label
        label?.let {
            Text(
                text = it,
                style = typography.caption.styles[CaptionType.C01]!!.medium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = spacing.spacing1)
            )
        }

        // Input box
        Box(
            modifier = Modifier
                .height(height)
                .clip(RoundedCornerShape(cornerRadius))
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(cornerRadius)
                )
                .background(backgroundColor)
                .padding(horizontal = horizontalPadding),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.spacing2),
                modifier = Modifier.fillMaxWidth()
            ) {

                leftIcon?.invoke(iconSize)

                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = textStyle,
                            color = Color.Gray
                        )
                    }
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        enabled = enabled,
                        textStyle = textStyle.copy(color = Color.Black),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                rightIcon?.invoke(iconSize)
            }
        }

        // Caption
        caption?.let {
            Text(
                text = it,
                style = typography.caption.styles[CaptionType.C01]!!.regular,
                color = borderColor,
                modifier = Modifier.padding(top = spacing.spacing1)
            )
        }
    }
}
