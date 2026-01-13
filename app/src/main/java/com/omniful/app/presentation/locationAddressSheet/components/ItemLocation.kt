package com.omniful.app.presentation.locationAddressSheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.omniful.app.R
import com.omniful.designsystem.components.onClickWithHaptics
import com.omniful.designsystem.theme.Black100
import com.omniful.designsystem.theme.BodyType
import com.omniful.designsystem.theme.Grey300
import com.omniful.designsystem.theme.LocalTypography

@Composable
fun ItemLocation(
    modifier: Modifier = Modifier,
    title: String?,
    subTitle: String?,
    onClick: () -> Unit,
) {
    val typography = LocalTypography.current

    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth().onClickWithHaptics {
            onClick()
        },
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .size(36.dp)
                .background(color = Color(0xFFE9FFEB)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(id = R.drawable.location_icon),
                contentDescription = "Location",
                tint = Color(0xFF2C8335),
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = title ?: "", style =  typography.body.styles[BodyType.B01]!!.medium.copy(textAlign = TextAlign.Center), color = Black100)
            Spacer(modifier = Modifier.height(1.dp))
            Text(text = subTitle ?: "", style =  typography.body.styles[BodyType.B03]!!.regular.copy(textAlign = TextAlign.Center), color = Grey300)
        }
    }

}
