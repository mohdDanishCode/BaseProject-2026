package com.omniful.app.presentation.login

import com.omniful.designsystem.components.countrycodepicker.CountryCode
import com.omniful.designsystem.components.countrycodepicker.defaultCountryCode

data class LoginUiState(
    val phoneNumber: String = "",
    val countryCode: CountryCode = defaultCountryCode(),
    val isLoading: Boolean = false,
    val isContinueEnabled: Boolean = false,

    )