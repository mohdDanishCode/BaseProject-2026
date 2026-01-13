package com.omniful.app.presentation.permission

import android.app.Application
import android.os.Build
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.core.coreDomain.model.address.OMFLatLng
import com.omniful.data.manager.location.LocationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    application: Application,
    private val locationManager: LocationManager,
) : ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

//    val currentLocationAddress = locationManager.currentAddress.asFlow().shareIn(viewModelScope, started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000))


    fun dismissDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE + 1) { // API level 35
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                visiblePermissionDialogQueue.removeFirst()
            }
            else{
                visiblePermissionDialogQueue.removeAt(0)
            }
        } else {
            visiblePermissionDialogQueue.removeAt(0)
        }
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean,
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }

//    fun getLocationWithLocation() {
//        locationManager.getCurrentLocation()
//    }
//
    fun getCurrentLocationAndAddress(currentLatLng: (OMFLatLng) -> Unit) {
        locationManager.getCurrentLocation({
            currentLatLng(it)
        }, { errorMessage ->
//            currentLatLng(
//                OMFLatLng(
//                    AppPreference.getSelectedAddress()?.latitude ?: 0.0,
//                    AppPreference.getSelectedAddress()?.longitude
//                        ?: 0.0,
//                ),
//            )
            OMFLatLng(
                 0.0,
                0.0,
            )
        })
    }
}
