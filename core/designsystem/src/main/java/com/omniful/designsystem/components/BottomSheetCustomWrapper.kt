package com.omniful.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.omniful.designsystem.R

@Composable
fun BottomSheetCustomWrapper(
    openBottomSheet: MutableState<Boolean>,
    restrictBackClick: Boolean = false,
    height: Dp? = null,
    sheetContent: @Composable ColumnScope.() -> Unit,
) {
    val pxValue = LocalDensity.current.density * (height?.value ?: 0.0f)
    val focusManager = LocalFocusManager.current

    val parentModifierHeight = if (height == null) Modifier.wrapContentHeight() else Modifier.height(height)
    val contentModifierHeight = if (height == null) Modifier.wrapContentHeight() else Modifier.fillMaxHeight()

    if (openBottomSheet.value) {
        BottomSheetDialog(
            properties = BottomSheetDialogProperties(
                dismissOnBackPress = !restrictBackClick,
                dismissOnClickOutside = !restrictBackClick,
                enableEdgeToEdge = false,
                behaviorProperties = BottomSheetBehaviorProperties(
                    state = BottomSheetBehaviorProperties.State.Expanded,
                    peekHeight = if (pxValue <= 0.0f) {
                        BottomSheetBehaviorProperties.PeekHeight.Auto
                    } else {
                        BottomSheetBehaviorProperties.PeekHeight(
                            pxValue.toInt(),
                        )
                    },
                    isDraggable = !restrictBackClick,
                ),
            ),
            onDismissRequest = {
                openBottomSheet.value = false
                focusManager.clearFocus()
            },
        ) {
            Column(
                modifier = parentModifierHeight,
            ) {
                if (!restrictBackClick) {
                    Image(
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                openBottomSheet.value = false
                            },
                        painter = painterResource(id = R.drawable.cross_circle),
                        contentDescription = "cross",
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Column(
                    modifier = contentModifierHeight
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
                        .background(Color.White),
                ) {
                    sheetContent()
                }
            }
        }
    }
}
