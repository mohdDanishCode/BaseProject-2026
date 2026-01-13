package com.omniful.app.presentation.splash

import androidx.lifecycle.ViewModel
import com.omniful.data.repository.user.UserMainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val userMainRepository: UserMainRepository
) : ViewModel() {
}