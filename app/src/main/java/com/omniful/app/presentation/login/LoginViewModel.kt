package com.omniful.app.presentation.login

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omniful.data.repository.user.UserMainRepository
import com.omniful.designsystem.components.countrycodepicker.CountryCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
     val userMainRepository: UserMainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onPhoneChange(phone: String) {
        if(!phone.isDigitsOnly() ||  phone.length > uiState.value.countryCode.phoneNoLength) return
        _uiState.update {
            it.copy(
                phoneNumber = phone,
                isContinueEnabled = phone.length == uiState.value.countryCode.phoneNoLength
            )
        }
    }

    fun onCountryCodeChange(countryCode: CountryCode) {
        _uiState.update {
            it.copy(
                countryCode = countryCode,
                isContinueEnabled = false,
                phoneNumber = ""
            )
        }
    }

    fun onContinueClicked(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000) // API / OTP trigger
            _uiState.update { it.copy(isLoading = false) }
            onSuccess()
        }
    }
}