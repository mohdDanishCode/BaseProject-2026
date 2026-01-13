package com.omniful.app.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.omniful.app.presentation.locationAddressSheet.SelectLocationBottomSheetContent
import com.omniful.designsystem.components.BottomSheetCustomWrapper
import com.omniful.designsystem.components.onClickWithHaptics
import com.omniful.designsystem.theme.BodyType
import com.omniful.designsystem.theme.CaptionType
import com.omniful.designsystem.theme.LocalOMFColors
import com.omniful.designsystem.theme.LocalOMFSize
import com.omniful.designsystem.theme.LocalTypography
import com.omniful.designsystem.theme.PandaSearchInputField
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen() {

    DashboardHeader()
}

@Composable
fun DashboardHeader(
    modifier: Modifier = Modifier
) {
    val colors = LocalOMFColors.current
    val spacing = LocalOMFSize.current.spacing
    val coroutineScope = rememberCoroutineScope()
    val localConfiguration = LocalConfiguration.current
    val openLocationBottomSheet = rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.background)
            .padding(horizontal = spacing.spacing4)
            .padding(top = spacing.spacing6, bottom = spacing.spacing4)
    ) {
        LocationRow(){
            openLocationBottomSheet.value=true
        }
        Spacer(modifier = Modifier.height(spacing.spacing3))
        PandaSearchInputField(
            onValueChange = { query ->
                // search logic / debounce / analytics
            }
        )
        Spacer(modifier = Modifier.height(spacing.spacing4))
        CategoryRow()
    }

    BottomSheetCustomWrapper(openBottomSheet = openLocationBottomSheet, height = (localConfiguration.screenHeightDp * 0.9).dp) {
        SelectLocationBottomSheetContent(
            onCLickNearByOrSearchedAddress = { omfLatLong ->

            },
        )
    }
}

@Composable
private fun LocationRow(onCLickLocation:()-> Unit) {
    val typography = LocalTypography.current
    val spacing = LocalOMFSize.current.spacing

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.onClickWithHaptics{
            onCLickLocation()
        }.weight(1f)) {
            Text(
                text = "Sector 15 Part 2",
                style = typography.body.styles[BodyType.B01]!!.semiBold
            )
            Spacer(modifier = Modifier.height(spacing.spacing1))
            Text(
                text = "Sector 15, Gurugram",
                style = typography.caption.styles[CaptionType.C01]!!.regular
            )
        }

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = "Bookmark"
            )
        }

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "Profile"
            )
        }
    }
}

@Composable
private fun CategoryRow() {
    val spacing = LocalOMFSize.current.spacing

    val categories = listOf(
        "For You" to Icons.Outlined.AutoAwesome,
        "Dining" to Icons.Outlined.Restaurant,
        "Events" to Icons.Outlined.MusicNote,
        "Movies" to Icons.Outlined.Movie,
        "Stores" to Icons.Outlined.Store,
        "Activities" to Icons.Outlined.EmojiEvents
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(spacing.spacing4)
    ) {
        items(categories) { (label, icon) ->
            CategoryItem(label, icon)
        }
    }
}

@Composable
private fun CategoryItem(
    label: String,
    icon: ImageVector
) {
    val typography = LocalTypography.current
    val spacing = LocalOMFSize.current.spacing

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label
        )
        Spacer(modifier = Modifier.height(spacing.spacing1))
        Text(
            text = label,
            style = typography.caption.styles[CaptionType.C01]!!.medium
        )
    }
}
