package com.omniful.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omniful.designsystem.theme.Black100
import com.omniful.designsystem.theme.HeadingType
import com.omniful.designsystem.theme.LocalOMFSize
import com.omniful.designsystem.theme.LocalTypography

const val OTP_VIEW_TYPE_NONE = 0
const val OTP_VIEW_TYPE_UNDERLINE = 1
const val OTP_VIEW_TYPE_BORDER = 2

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OtpView(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    otpText: String = "",
    charColor: Color = Color.Black,
    charBackground: Color = Color.Transparent,
    charSize: TextUnit = 16.sp,
    containerHeight: Dp = charSize.value.dp* 2,
    containerWidth: Dp = charSize.value.dp* 2,
    otpCount: Int = 4,
    shape:CornerBasedShape=MaterialTheme.shapes.medium,
    type: Int = OTP_VIEW_TYPE_UNDERLINE,
    enabled: Boolean = true,
    password: Boolean = false,
    passwordChar: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onOtpTextChange: (String) -> Unit,
    textStyle: TextStyle = LocalTypography.current.heading.styles[HeadingType.H03]!!.medium,
    charFocusColor: Color = Color.DarkGray,
) {

    val size = LocalOMFSize.current
    var focusIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    LaunchedEffect(key1 = otpText) {
        if (otpText.isEmpty()) {
            focusIndex = 0
        }
    }

    BasicTextField(
        modifier = modifier,
        value = otpText,
        onValueChange = {
            val value = it.replace("[^0-9]".toRegex(), "")
            if (value.length <= otpCount) {
                focusIndex = value.length
                onOtpTextChange.invoke(value)
            }
        },
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(Black100),
        decorationBox = {

            Row(modifier=Modifier, horizontalArrangement = horizontalArrangement) {
                repeat(otpCount) { index ->
                    CharView(
                        modifier_= Modifier.height(containerHeight)
                            .width(containerWidth).weight(1f,false),
                        index = index,
                        text = otpText,
                        charColor = charColor,
                        charSize = charSize,

                        type = type,
                        charBackground = charBackground,
                        password = password,
                        passwordChar = passwordChar,
                        textStyle = textStyle,
                        isFocusIndex = focusIndex == index,
                        focusColor = charFocusColor,
                        shape=shape,
                        containerHeight=containerHeight,
                        containerWidth=containerWidth
                    )

                    if(horizontalArrangement != Arrangement.SpaceBetween && index<otpCount-1)
                        Spacer(modifier = Modifier.width(size.spacing.spacing2))
                }
            }



        },
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    charColor: Color,
    charSize: TextUnit,
    containerHeight: Dp,
    containerWidth: Dp,
    type: Int = OTP_VIEW_TYPE_UNDERLINE,
    charBackground: Color = Color.Transparent,
    password: Boolean = false,
    passwordChar: String = "",
    textStyle: TextStyle,
    isFocusIndex: Boolean = false,
    focusColor: Color,
    shape: CornerBasedShape,
    modifier_: Modifier,
) {
    val modifier = if (type == OTP_VIEW_TYPE_BORDER) {
        modifier_
            .border(
                width = if (isFocusIndex) 1.dp else 0.5.dp ,
                color = if (isFocusIndex) focusColor else charColor,
                shape = shape,
            )
            .padding(bottom = 4.dp)
            .background(charBackground)
    } else {
        modifier_
            .background(charBackground)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val char = when {
            index >= text.length -> ""
            password -> passwordChar
            else -> text[index].toString()
        }
        Text(
            text = char,
            modifier = modifier.wrapContentHeight(),
            style = textStyle.copy(platformStyle = PlatformTextStyle(includeFontPadding = false)),
            fontSize = charSize,
            textAlign = TextAlign.Center,
        )
        if (type == OTP_VIEW_TYPE_UNDERLINE) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .background(charColor)
                    .height(1.dp)
                    .width(containerWidth),
            )
        }
    }
}