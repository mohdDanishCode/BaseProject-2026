package com.omniful.app.presentation.locationAddressSheet.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.omniful.app.R
import com.omniful.designsystem.components.onClickWithHaptics
import com.omniful.designsystem.theme.CaptionType
import com.omniful.designsystem.theme.LocalOMFColors
import com.omniful.designsystem.theme.LocalTypography
import com.omniful.designsystem.theme.OMFTypography

@Composable
fun CurrentLocationButton(
    text: String,
    modifier: Modifier = Modifier,
    showBorder: Boolean = false,
    onCLick: () -> Unit,
) {
    val typography = LocalTypography.current
    val color = LocalOMFColors.current
    Row(
        modifier = modifier
            .onClickWithHaptics {
                onCLick()
            }
            .clip(shape = RoundedCornerShape(6.dp))
            .background(color = Color.White)
            .border(
                border = BorderStroke(
                    1.dp,
                    color = if (showBorder) color.primary else Color.Transparent,
                ),
                shape = RoundedCornerShape(6.dp),
            )
            .padding(horizontal = 10.7.dp, vertical = 6.79.dp),
    ) {
        Image(painter = painterResource(id = R.drawable.current_address), contentDescription = "Pointer", colorFilter = ColorFilter.tint(color.primary))
        Spacer(modifier = Modifier.width(6.79.dp))
        Text(text, style =typography.caption.styles[CaptionType.C01]!!.medium.copy(textAlign = TextAlign.Center), color = color.primary)
    }
}
