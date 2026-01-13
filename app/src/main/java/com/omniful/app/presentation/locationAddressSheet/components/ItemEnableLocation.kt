package com.omniful.app.presentation.locationAddressSheet.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omniful.app.R
import com.omniful.designsystem.theme.BodyType
import com.omniful.designsystem.theme.CaptionType
import com.omniful.designsystem.theme.Grey200
import com.omniful.designsystem.theme.HeadingType
import com.omniful.designsystem.theme.LocalOMFColors
import com.omniful.designsystem.theme.LocalTypography
import com.omniful.designsystem.theme.mirror

@Composable
@Preview
fun ItemEnableLocation(
    modifier: Modifier = Modifier,
) {
    val typography = LocalTypography.current
    val color = LocalOMFColors.current
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Image(painter = painterResource(id = R.drawable.no_location_icon), contentDescription = null)
        Column {
            Row(
                verticalAlignment = Alignment.Top,
            ) {
                Text(modifier = Modifier.weight(1f), style = typography.heading.styles[HeadingType.H06]!!.semiBold.copy(textAlign = TextAlign.Center), text = "Location permission is off")
                Row(
                    modifier = Modifier.padding(top = 1.6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Enable now",
                        color = color.primary,
                        style = typography.body.styles[BodyType.B03]!!.medium.copy(textAlign = TextAlign.Center),
                    )

                    Icon(modifier = modifier.mirror(), painter = painterResource(id = R.drawable.next_icon), contentDescription = "Next Icon", tint = color.primary)

                }
            }
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = "Discover exclusive deals and relevant promotions based on your location access.",
                color = Grey200,
                style = typography.caption.styles[CaptionType.C01]!!.medium.copy(textAlign = TextAlign.Center),
            )
        }
    }
}
