package com.omniful.app.presentation.otp

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omniful.app.presentation.login.LoginUiState
import com.omniful.data.repository.user.UserMainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject



@HiltViewModel
class OtpViewModel @Inject constructor(
    val userMainRepository: UserMainRepository
) : ViewModel() {

    companion object {
        const val OTP_TIME_SECOND = 60
    }
    private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable -> }

    var emailPhoneNumber = mutableStateOf<String?>(null)
    var password = mutableStateOf<String?>(null)
    private val _uiState = MutableStateFlow(OtpUiState())
    val uiState: StateFlow<OtpUiState> = _uiState.asStateFlow()

    var timerJob: Job? = null
    var otpValue: MutableState<String> = mutableStateOf("")
    var otpCount = mutableIntStateOf(6)

    var expirationTimestamp : String ?= null

    fun getExpiryTimestamp(): String {
        return Instant.now()
            .plus(10, ChronoUnit.SECONDS)
            .toString()
    }
    val isValidOtp = derivedStateOf {
        otpValue.value.length == otpCount.intValue
    }

    var timerValue: MutableState<String> = mutableStateOf(OTP_TIME_SECOND.toString())
    var enableRetryField = mutableStateOf(false)


    @SuppressLint("DefaultLocale")
    fun initializeTimer() {
        expirationTimestamp?.let { expireTime->
            otpValue.value = ""
            stopTimer()
            timerJob = viewModelScope.launch(coroutineExceptionHandler) {
                enableRetryField.value = false

                startOtpTimer(expireTime){timeLeft ->
                    val minutes = timeLeft / 60
                    val seconds = timeLeft % 60
//                    timerValue.value = String.format("%02d:%02d", minutes, seconds)
                    timerValue.value = when {
                        minutes > 0 -> "$minutes minute${if (minutes > 1) "s" else ""} ${if (seconds > 0) "and $seconds second${if (seconds > 1) "s" else ""}" else ""}"
                        else -> "$seconds second${if (seconds > 1) "s" else ""}"
                    }
                }
                enableRetryField.value = true
            }
        }

    }


    private suspend fun startOtpTimer(expirationTimestamp: String, onTick: (Long) -> Unit) {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSX", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val targetDate: Date? = sdf.parse(expirationTimestamp)
        val currentTime = System.currentTimeMillis()

        // Calculate time difference in seconds

        // FIXME: ((targetDate?.time ?: 0L) - currentTime) / 1000
        var timeLeft = 120L

        while (timeLeft > 0) {
            onTick(timeLeft)
            delay(1000L)  // Wait for 1 second
            timeLeft -= 1
        }

        // When the timer finishes, handle it here
        onTick(0)  // Last update with 00:00
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }

    fun cancelTimer(){
        otpValue.value = ""
        timerJob?.cancel()
        enableRetryField.value = true
    }


    fun login(){

    }
    fun sendOtpAndLogin(){

    }
}