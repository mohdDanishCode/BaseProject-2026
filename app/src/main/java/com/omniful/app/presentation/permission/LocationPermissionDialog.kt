package com.omniful.app.presentation.permission

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.omniful.data.permission.PermissionUtils.locationPermissionsToRequest
import kotlinx.coroutines.launch

@Composable
fun LocationPermissionDialog(
    activity: Activity?,
    askForPermissionCount: Int = 0,
    onGranted: () -> Unit,
    onDenied: () -> Unit,
    showRational: Boolean = true,
) {
    val viewModel = hiltViewModel<PermissionViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            if (perms[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                coroutineScope.launch {
                    onGranted()
                }
            } else {
                if (showRational) {
                    viewModel.onPermissionResult(
                        permission = Manifest.permission.ACCESS_FINE_LOCATION,
                        isGranted = false,
                    )
                }
                onDenied()
            }
        },
    )

    LaunchedEffect(key1 = askForPermissionCount) {
        if (askForPermissionCount != 0) {
            multiplePermissionResultLauncher.launch(locationPermissionsToRequest)
        }
    }

    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialogCustom(
                permissionCustomTextProvider = when (permission) {
                    Manifest.permission.ACCESS_FINE_LOCATION -> {
                        AccessFineLocationPermissionTextProvider()
                    }

                    else -> return@forEach
                },
                isPermanentlyDeclined = activity?.let {
                    !ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        permission,
                    )
                } ?: false,
                onDismiss = viewModel::dismissDialog,
                onOkClick = {
                    viewModel.dismissDialog()
                    multiplePermissionResultLauncher.launch(
                        arrayOf(permission),
                    )
                },
                onGoToAppSettingsClick = {
                    viewModel.dismissDialog()
                    (activity)?.openAppSettings()
                },
                onClickSecondCta = {
                    viewModel.dismissDialog()
                },
            )
        }
}
