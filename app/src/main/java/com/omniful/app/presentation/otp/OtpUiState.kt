package com.omniful.app.presentation.otp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.omniful.designsystem.components.countrycodepicker.CountryCode
import com.omniful.designsystem.components.countrycodepicker.defaultCountryCode
import kotlinx.coroutines.Job


data class OtpUiState(
    var timerJob: Job? = null,
    var otpValue: MutableState<String> = mutableStateOf(""),
    val isLoading: Boolean = false,
    val isContinueEnabled: Boolean = false,

    )