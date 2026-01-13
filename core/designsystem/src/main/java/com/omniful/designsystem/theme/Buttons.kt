package com.omniful.designsystem.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.omniful.designsystem.R
import com.omniful.designsystem.components.onClickWithHaptics

enum class ButtonSize {
    XS, S, M, L
}

@Composable
fun PrimaryButton(
    label: String,
    size: ButtonSize = ButtonSize.M,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonColor: Color?=null,
    isLoading:Boolean = true,
    leftIcon: (@Composable ((Dp) -> Unit))? = null,
    rightIcon: (@Composable ((Dp) -> Unit))? = null
) {
    val colors = LocalOMFColors.current
    val shadows = LocalShadows.current
    val spacing = LocalOMFSize.current.spacing
    val radius = LocalOMFSize.current.radius
    val typography = LocalTypography.current

    // 🔹 Size mapping inline (simple & readable)
    val height: Dp
    val horizontalPadding: Dp
    val cornerRadius: Dp
    val textStyle: TextStyle
    val iconSize: Dp

    when (size) {
        ButtonSize.L -> {
            height = 48.dp
            horizontalPadding = spacing.spacing3
            cornerRadius = radius.radius3
            textStyle =  typography.body.styles[BodyType.B01]!!.semiBold
            iconSize = 24.dp
        }
        ButtonSize.M -> {
            height = 40.dp
            horizontalPadding = spacing.spacing3
            cornerRadius = radius.radius3
            textStyle =  typography.body.styles[BodyType.B02]!!.semiBold
            iconSize = 16.dp
        }
        ButtonSize.S -> {
            height = 32.dp
            horizontalPadding = spacing.spacing2
            cornerRadius = radius.radius3
            textStyle =  typography.caption.styles[CaptionType.C01]!!.semiBold
            iconSize = 16.dp
        }
        ButtonSize.XS -> {
            height = 24.dp
            horizontalPadding = spacing.spacing1
            cornerRadius = radius.radius3
            textStyle = typography.caption.styles[CaptionType.C01]!!.semiBold
            iconSize = 16.dp
        }
    }

    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .background(
                color = if (enabled) buttonColor?:colors.primary else buttonColor?.copy(alpha = 0.5f)?:colors.primary.copy(alpha = 0.5f)
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = horizontalPadding),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.spacing2)
        ) {

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            }
            else{
                leftIcon?.invoke(iconSize)

                Text(
                    text = label,
                    style = textStyle,
                    color = Color.White
                )

                rightIcon?.invoke(iconSize)
            }
        }
    }
}

@Composable
fun SecondaryButton(
    label: String,
    size: ButtonSize = ButtonSize.M,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonColor: Color?=null,
    leftIcon: (@Composable ((Dp) -> Unit))? = null,
    rightIcon: (@Composable ((Dp) -> Unit))? = null
) {
    val colors = LocalOMFColors.current
    val spacing = LocalOMFSize.current.spacing
    val radius = LocalOMFSize.current.radius
    val typography = LocalTypography.current

    val height: Dp
    val horizontalPadding: Dp
    val cornerRadius: Dp
    val textStyle: TextStyle
    val iconSize: Dp
    val borderWidth = 1.dp

    when (size) {
        ButtonSize.L -> {
            height = 48.dp
            horizontalPadding = spacing.spacing3
            cornerRadius = radius.radius3
            textStyle = typography.body.styles[BodyType.B01]!!.semiBold
            iconSize = 24.dp
        }
        ButtonSize.M -> {
            height = 40.dp
            horizontalPadding = spacing.spacing3
            cornerRadius = radius.radius3
            textStyle = typography.body.styles[BodyType.B02]!!.semiBold
            iconSize = 16.dp
        }
        ButtonSize.S -> {
            height = 32.dp
            horizontalPadding = spacing.spacing2
            cornerRadius = radius.radius3
            textStyle = typography.caption.styles[CaptionType.C01]!!.semiBold
            iconSize = 16.dp
        }
        ButtonSize.XS -> {
            height = 24.dp
            horizontalPadding = spacing.spacing1
            cornerRadius = radius.radius3
            textStyle = typography.caption.styles[CaptionType.C01]!!.semiBold
            iconSize = 16.dp
        }
    }

    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .border(
                width = borderWidth,
                color = if (enabled) colors.primary else colors.primary.copy(alpha = 0.5f),
                shape = RoundedCornerShape(cornerRadius)
            )
            .background(buttonColor?:Transparent)
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = horizontalPadding),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.spacing2)
        ) {

            leftIcon?.invoke(iconSize)

            if(label.isNotEmpty()){
                Text(
                    text = label,
                    style = textStyle,
                    color = if (enabled) Color.Black else Color.Black.copy(alpha = 0.5f)
                )
            }



            rightIcon?.invoke(iconSize)
        }
    }
}

@Composable
fun TertiaryButton(
    label: String,
    size: ButtonSize = ButtonSize.M,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonColor: Color?=null,
    leftIcon: (@Composable ((Dp) -> Unit))? = null,
    rightIcon: (@Composable ((Dp) -> Unit))? = null
) {
    val colors = LocalOMFColors.current
    val spacing = LocalOMFSize.current.spacing
    val radius = LocalOMFSize.current.radius
    val typography = LocalTypography.current

    val height: Dp
    val horizontalPadding: Dp
    val cornerRadius: Dp
    val textStyle: TextStyle
    val iconSize: Dp
    val borderWidth = 1.dp

    when (size) {
        ButtonSize.L -> {
            height = 48.dp
            horizontalPadding = spacing.spacing3
            cornerRadius = radius.radius3
            textStyle = typography.body.styles[BodyType.B01]!!.semiBold
            iconSize = 24.dp
        }
        ButtonSize.M -> {
            height = 40.dp
            horizontalPadding = spacing.spacing3
            cornerRadius = radius.radius3
            textStyle = typography.body.styles[BodyType.B02]!!.semiBold
            iconSize = 16.dp
        }
        ButtonSize.S -> {
            height = 32.dp
            horizontalPadding = spacing.spacing2
            cornerRadius = radius.radius3
            textStyle = typography.caption.styles[CaptionType.C01]!!.semiBold
            iconSize = 16.dp
        }
        ButtonSize.XS -> {
            height = 24.dp
            horizontalPadding = spacing.spacing1
            cornerRadius = radius.radius3
            textStyle = typography.caption.styles[CaptionType.C01]!!.semiBold
            iconSize = 16.dp
        }
    }

    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .border(
                width = borderWidth,
                color = if (enabled) Color(0x05000000) else Color(0x05000000).copy(alpha = 0.5f),
                shape = RoundedCornerShape(cornerRadius)
            )
            .background(buttonColor?:Transparent)
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = horizontalPadding),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.spacing2)
        ) {

            leftIcon?.invoke(iconSize)

            Text(
                text = label,
                style = textStyle,
                color = if (enabled) Color.Black else Color.Black.copy(alpha = 0.5f)
            )

            rightIcon?.invoke(iconSize)
        }
    }
}

@Composable
fun BackButton(modifier: Modifier,customText :String?=null, onBackClick: () -> Unit){
    val sizeSystem  = LocalOMFSize.current
    val color = LocalOMFColors.current
    val typography = LocalTypography.current

    Row(
        modifier = modifier
            .onClickWithHaptics {
                onBackClick()
            }
            .padding(sizeSystem.spacing.spacing4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(modifier = Modifier.size(sizeSystem.spacing.spacing6),painter = painterResource(R.drawable.back), contentDescription = null)
    }
}

@Composable
fun GhostButton(
    label: String,
    size: ButtonSize = ButtonSize.M,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leftIcon: (@Composable ((Dp) -> Unit))? = null,
    rightIcon: (@Composable ((Dp) -> Unit))? = null
) {
    val colors = LocalOMFColors.current
    val spacing = LocalOMFSize.current.spacing
    val radius = LocalOMFSize.current.radius
    val typography = LocalTypography.current

    val height: Dp
    val horizontalPadding: Dp
    val cornerRadius: Dp
    val textStyle: TextStyle
    val iconSize: Dp
    val borderWidth = 1.dp

    when (size) {
        ButtonSize.L -> {
            height = 48.dp
            horizontalPadding = spacing.spacing3
            cornerRadius = radius.radius3
            textStyle = typography.body.styles[BodyType.B01]!!.semiBold
            iconSize = 24.dp
        }
        ButtonSize.M -> {
            height = 40.dp
            horizontalPadding = spacing.spacing3
            cornerRadius = radius.radius3
            textStyle = typography.body.styles[BodyType.B02]!!.semiBold
            iconSize = 16.dp
        }
        ButtonSize.S -> {
            height = 32.dp
            horizontalPadding = spacing.spacing2
            cornerRadius = radius.radius3
            textStyle = typography.caption.styles[CaptionType.C01]!!.semiBold
            iconSize = 16.dp
        }
        ButtonSize.XS -> {
            height = 24.dp
            horizontalPadding = spacing.spacing1
            cornerRadius = radius.radius3
            textStyle = typography.caption.styles[CaptionType.C01]!!.semiBold
            iconSize = 16.dp
        }
    }

    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .background(
                color = if (enabled) Color.Transparent else Color.Transparent.copy(alpha = 0.5f)
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = horizontalPadding),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.spacing2)
        ) {

            leftIcon?.invoke(iconSize)

            Text(
                text = label,
                style = textStyle,
                color = if (enabled) Color.Black else Color.Black.copy(alpha = 0.5f)
            )

            rightIcon?.invoke(iconSize)
        }
    }
}
