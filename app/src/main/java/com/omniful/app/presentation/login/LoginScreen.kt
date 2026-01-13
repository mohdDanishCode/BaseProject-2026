package com.omniful.app.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.omniful.app.R
import com.omniful.app.presentation.countrycode.CountryCodeBottomSheetContent
import com.omniful.designsystem.components.BottomSheetCustomWrapper
import com.omniful.designsystem.components.countrycodepicker.CountryCode
import com.omniful.designsystem.components.countrycodepicker.getFlags
import com.omniful.designsystem.theme.Black100
import com.omniful.designsystem.theme.Blank
import com.omniful.designsystem.theme.ButtonSize
import com.omniful.designsystem.theme.CaptionType
import com.omniful.designsystem.theme.DividerColor
import com.omniful.designsystem.theme.HeadingType
import com.omniful.designsystem.theme.InputSize
import com.omniful.designsystem.theme.InputState
import com.omniful.designsystem.theme.LocalOMFColors
import com.omniful.designsystem.theme.LocalOMFSize
import com.omniful.designsystem.theme.LocalTypography
import com.omniful.designsystem.theme.OMFTextInput
import com.omniful.designsystem.theme.PrimaryButton
import com.omniful.designsystem.theme.SecondaryButton
import com.omniful.designsystem.theme.TertiaryButton
import com.omniful.designsystem.theme.Transparent
import com.omniful.designsystem.theme.White100
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onSkip: () -> Unit,
    onGoogleLogin: () -> Unit,
    onAppleLogin: () -> Unit,
    onContinueSuccess: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val typography = LocalTypography.current
    val size = LocalOMFSize.current
    val color = LocalOMFColors.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Blank)
    ) {

        Box(modifier = Modifier.fillMaxSize().background(Black100.copy(alpha = 0.3f))){

        }

        TertiaryButton(
            label = "Skip",
            size = ButtonSize.M,
            onClick = onSkip,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(horizontal = size.spacing.spacing5, vertical = size.spacing.spacing6 )
            ,
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.White, RoundedCornerShape(topStart = size.radius.radius9, topEnd =  size.radius.radius9))
                .padding(vertical = size.spacing.spacing6, horizontal = size.spacing.spacing5),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Your quick route to\ngood times",
                style = typography.heading.styles[HeadingType.H06]!!.bold.copy(textAlign = TextAlign.Center),
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height( size.spacing.spacing6))

            DividerWithText("LOG IN OR SIGN UP")

            Spacer(modifier = Modifier.height( size.spacing.spacing6))


            PhoneInput(
                countryCode = state.countryCode,
                phone = state.phoneNumber,
                onPhoneChange = viewModel::onPhoneChange,
                onCountryChange = {selectedCountry->
                    viewModel.onCountryCodeChange(selectedCountry)
                }
            )

            Spacer(modifier = Modifier.height( size.spacing.spacing6))

            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                label = "Continue",
                size = ButtonSize.L,
                onClick = {
                    viewModel.onContinueClicked(onContinueSuccess)
                },
                buttonColor = Black100,
                isLoading = state.isLoading,
                enabled = state.isContinueEnabled
            )

            Spacer(modifier = Modifier.height( size.spacing.spacing6))

            DividerWithText("OR")

            Spacer(modifier = Modifier.height( size.spacing.spacing6))

            SocialLoginButtons(
                onGoogleClick = onGoogleLogin,
                onAppleClick = onAppleLogin
            )

            Spacer(modifier = Modifier.height( size.spacing.spacing6))

            TermsText()

            Spacer(modifier = Modifier.height( size.spacing.spacing1))
        }



    }
}



@Composable
fun PhoneInput(
    countryCode: CountryCode,
    phone: String,
    onPhoneChange: (String) -> Unit,
    onCountryChange: (CountryCode) -> Unit,
) {
    val size = LocalOMFSize.current
    val typography = LocalTypography.current
    val coroutineScope = rememberCoroutineScope()
    val openCountryCodeSheet = remember { mutableStateOf(false) }
    val localConfiguration = LocalConfiguration.current

    Row(verticalAlignment = Alignment.CenterVertically) {

        CountryCodeButton(countryCode) {
            coroutineScope.launch {
                openCountryCodeSheet.value = true
            }
        }

        Spacer(Modifier.width(size.spacing.spacing2))

        OMFTextInput(
            value = phone,
            onValueChange = onPhoneChange,
            label = "",
            placeholder = "Phone Number",
            size = InputSize.L,
            state = InputState.Default,
            caption = "",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        )
    }

    BottomSheetCustomWrapper(openBottomSheet = openCountryCodeSheet, height = (localConfiguration.screenHeightDp * 0.9).dp) {
        CountryCodeBottomSheetContent(countryCode) { selectedCountry ->
            coroutineScope.launch {
                onCountryChange(selectedCountry)
                openCountryCodeSheet.value = false
            }
        }
    }
}


@Composable
fun CountryCodeButton(countryCode: CountryCode, onClick: () -> Unit) {
    val color = LocalOMFColors.current
    OutlinedButton(
        modifier = Modifier.height(48.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            contentColor = Black100,
            containerColor = White100,
        ),
        border = BorderStroke(0.5.dp, Color(0xFFCECECE)),
            shape = RoundedCornerShape( 12.dp),
        contentPadding = PaddingValues(
            top = 12.dp,
            bottom = 12.dp,
            start = 10.dp,
            end = 13.dp,
        ),

        ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(
                    getFlags(
                        countryCode.countryCode,
                    ),
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(25.dp)
                    .height(16.dp),
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = countryCode.countryPhoneCode,

            )
            Spacer(modifier = Modifier.width(9.28.dp))
            Icon(
                painter = painterResource(id = R.drawable.chevron_down),
                contentDescription = null,
                modifier = Modifier
                    .width(10.dp)
                    .height(6.dp),
                tint = Black100,
            )
        }
    }
}

@Composable
fun DividerWithText(text: String) {
    val typography = LocalTypography.current
    Row(verticalAlignment = Alignment.CenterVertically) {
        Divider(modifier = Modifier.weight(1f), color = DividerColor)
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp),
            style = typography.caption.styles[CaptionType.C01]!!.semiBold.copy(textAlign = TextAlign.Center),
        )
        Divider(modifier = Modifier.weight(1f), color = DividerColor)
    }
}

@Composable
fun SocialLoginButtons(
    onGoogleClick: () -> Unit,
    onAppleClick: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {



        TertiaryButton(
            label = "",
            size = ButtonSize.L,
            onClick = onGoogleClick,
            modifier = Modifier.weight(1f),
            buttonColor= Blank,
            leftIcon = {
                Image(painter = painterResource(R.drawable.material_icon_theme_google),null)
            }
        )

    }
}

@Composable
fun TermsText() {
    val size = LocalOMFSize.current
    val typography = LocalTypography.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "By continuing, you agree to our",
            style = typography.caption.styles[CaptionType.C01]!!.semiBold.copy(textAlign = TextAlign.Center),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.width(size.spacing.spacing2))
        Text(
            text = "Terms of Service • Privacy Policy",
            style = typography.caption.styles[CaptionType.C02]!!.medium.copy(textAlign = TextAlign.Center),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }

}


