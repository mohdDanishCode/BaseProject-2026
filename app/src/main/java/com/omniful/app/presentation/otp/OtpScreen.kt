package com.omniful.app.presentation.otp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omniful.app.R
import com.omniful.designsystem.components.OTP_VIEW_TYPE_BORDER
import com.omniful.designsystem.components.OtpView
import com.omniful.designsystem.theme.BackButton
import com.omniful.designsystem.theme.Black100
import com.omniful.designsystem.theme.BodyType
import com.omniful.designsystem.theme.ButtonSize
import com.omniful.designsystem.theme.CaptionType
import com.omniful.designsystem.theme.Grey25
import com.omniful.designsystem.theme.Grey300
import com.omniful.designsystem.theme.HeadingType
import com.omniful.designsystem.theme.LocalOMFColors
import com.omniful.designsystem.theme.LocalOMFSize
import com.omniful.designsystem.theme.LocalTypography
import com.omniful.designsystem.theme.PrimaryButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OtpScreen(
    userName: String,
    password: String,
    expireTime: String,
    canNavigate: () -> Unit,
    onClickBack: () -> Unit
) {

    val size = LocalOMFSize.current
    val color = LocalOMFColors.current
    val typography = LocalTypography.current

    val loginViewModel = hiltViewModel<OtpViewModel>()


    val context = LocalContext.current


    LaunchedEffect(Unit) {
        loginViewModel.emailPhoneNumber.value = userName
        loginViewModel.password.value = password
//        loginViewModel.expirationTimestamp = expireTime
        loginViewModel.expirationTimestamp = loginViewModel.getExpiryTimestamp()
        loginViewModel.initializeTimer()
    }

    var showLoading by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    var otpValue by loginViewModel.otpValue
    val otpCount by loginViewModel.otpCount

    val isValidOtp by loginViewModel.isValidOtp

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BackButton(modifier = Modifier.align(Alignment.Start)) {
            onClickBack()
        }

        Column(
            modifier = Modifier.padding(
                horizontal = size.spacing.spacing6,
                vertical = size.spacing.spacing6
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Enter Verification Code",
                style = typography.heading.styles[HeadingType.H06]!!.semiBold.copy(textAlign = TextAlign.Center)
            )
            Spacer(modifier = Modifier.height(size.spacing.spacing2))

            Text(
                text = "We’ve sent a verification code to your phone number",
                textAlign = TextAlign.Center,
                style = typography.body.styles[BodyType.B02]!!.medium.copy(textAlign = TextAlign.Center)
            )

            Spacer(modifier = Modifier.height(size.spacing.spacing8))
            OtpView(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (it.isFocused) {
                            coroutineScope.launch {
                                delay(420)
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    }
                    .focusRequester(focusRequester),
                otpText = otpValue,
                onOtpTextChange = {
                    if (it.length == otpCount) {
                        focusManager.clearFocus()
                    }
                    otpValue = it
                },
                charSize = 24.sp,
                type = OTP_VIEW_TYPE_BORDER,
                password = false,
//            shape = sizeSystem.shape(sizeSystem.cornerRadius3),
//            containerHeight = sizeSystem.textFieldHeight,
//            containerWidth = sizeSystem.otpWidth,
                charBackground = Color.White,
//            charColor = B1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    },
                ),
                otpCount = otpCount,
//            textStyle = typography.h2Regular(),
                charFocusColor = color.primary
            )

            Spacer(modifier = Modifier.height(size.spacing.spacing4))

            if (loginViewModel.enableRetryField.value) {
                Row(
                    modifier = Modifier
                        .clickable {
                            loginViewModel.login()
                        }
                        .padding(start = 12.dp)
                        .align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(size.spacing.spacing2, Alignment.CenterHorizontally)
                ) {
                    Image(
                        colorFilter = ColorFilter.tint(color.primary),
                        modifier = Modifier.size(size = size.spacing.spacing4),
                        painter = painterResource(R.drawable.refresh),
                        contentDescription = null,
                    )
                    Text(
                        modifier = Modifier,
                        text = "resend",
                        color = color.primary,
                        style = typography.body.styles[BodyType.B01]!!.semiBold.copy(textAlign = TextAlign.Center)
                    )
                }


            } else {
                Row(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Resend code in" + " ",
                        color = Grey300,
                        style = typography.body.styles[BodyType.B01]!!.medium.copy(textAlign = TextAlign.Center)

                        )
                    Text(
                        modifier = Modifier, text = loginViewModel.timerValue.value ?: "",
                        color = Grey300,
                        style = typography.body.styles[BodyType.B01]!!.semiBold.copy(textAlign = TextAlign.Center)
                    )
                }
            }


            Spacer(modifier = Modifier.height(size.spacing.spacing8))
            Spacer(modifier = Modifier.height(size.spacing.spacing8))


            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                label = "verify",
                size = ButtonSize.L,
                onClick = {
                    loginViewModel.sendOtpAndLogin()
                    canNavigate()
                },
                buttonColor = Black100,
                isLoading = false,
                enabled = loginViewModel.isValidOtp.value
            )

        }
    }


}