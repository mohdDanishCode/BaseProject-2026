package com.omniful.app.presentation.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.omniful.app.R
import com.omniful.designsystem.theme.Black100
import com.omniful.designsystem.theme.Blank
import com.omniful.designsystem.theme.ButtonSize
import com.omniful.designsystem.theme.PrimaryButton
import com.omniful.designsystem.theme.TertiaryButton

@Composable
fun PermissionDialogCustom(
    onDismiss: () -> Unit,
    permissionCustomTextProvider: PermissionCustomTextProvider,
    isPermanentlyDeclined: Boolean,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    onClickSecondCta: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(20.dp),
            color = Color.White,

        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                permissionCustomTextProvider.getImageResID()?.let {
                    Image(modifier = Modifier.padding(top = 8.dp).size(140.dp), painter = painterResource(id = it), contentDescription = null)
                }

                Text(modifier = Modifier.padding(top = 18.dp), text = permissionCustomTextProvider.getTitle(context),)
                Text(modifier = Modifier.padding(top = 16.dp), text = permissionCustomTextProvider.getDescription(isPermanentlyDeclined, context), textAlign = TextAlign.Center)



                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Continue",
                    size = ButtonSize.L,
                    onClick = {
                        if (isPermanentlyDeclined) {
                            onGoToAppSettingsClick()
                        } else {
                            onOkClick()
                        }
                    },
                    buttonColor = Black100,
                    isLoading = false,
                    enabled = true
                )

                TertiaryButton(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    label = "",
                    size = ButtonSize.L,
                    onClick = {
                        onClickSecondCta()
                    },
                )

            }
        }
    }
}

interface PermissionCustomTextProvider {
    fun getImageResID(): Int?
    fun getTitle(context: Context): String
    fun getDescription(isPermanentlyDeclined: Boolean, context: Context): String
    fun getFirstCtaTitle(isPermanentlyDeclined: Boolean, context: Context): String
    fun getSecondCtaTitle(context: Context): String
}

//class CameraPermissionTextProvider : PermissionCustomTextProvider {
//
//    override fun getImageResID(): Int? {
//        return null
//    }
//
//    override fun getTitle(context: Context): String {
//        return context.getString(com.danish.common.R.string.camera_access)
//    }
//
//    override fun getDescription(isPermanentlyDeclined: Boolean, context: Context): String {
//        return if (isPermanentlyDeclined) {
//            context.getString(com.danish.common.R.string.it_seems_you_permanently_declined_camera_permission_you_can_go_to_the_app_settings_to_grant_it)
//        } else {
//            context.getString(com.danish.common.R.string.this_app_needs_access_to_your_camera_so_that_your_friends_can_see_you_in_a_call)
//        }
//    }
//
//    override fun getFirstCtaTitle(isPermanentlyDeclined: Boolean, context: Context): String {
//        return if (isPermanentlyDeclined) {
//            context.getString(com.danish.common.R.string.allow_in_settings)
//        } else {
//            context.getString(com.danish.common.R.string.allow_camera_access)
//        }
//    }
//
//    override fun getSecondCtaTitle(context: Context): String {
//        return context.getString(com.danish.common.R.string.i_will_do_it_later)
//    }
//}
//class ReadContactPermissionTextProvider : PermissionCustomTextProvider {
//    override fun getImageResID(): Int? {
//        return null
//    }
//
//    override fun getTitle(context: Context): String {
//        return context.getString(com.danish.common.R.string.read_contact_access)
//    }
//
//    override fun getDescription(isPermanentlyDeclined: Boolean, context: Context): String {
//        return if (isPermanentlyDeclined) {
//            context.getString(com.danish.common.R.string.it_seems_you_permanently_declined_read_contact_permission_you_can_go_to_the_app_settings_to_grant_it)
//        } else {
//            context.getString(com.danish.common.R.string.this_app_needs_access_to_your_contact_so_that_you_can_select_phone_number_directly)
//        }
//    }
//
//    override fun getFirstCtaTitle(isPermanentlyDeclined: Boolean, context: Context): String {
//        return if (isPermanentlyDeclined) {
//            context.getString(com.danish.common.R.string.allow_in_settings)
//        } else {
//            context.getString(com.danish.common.R.string.allow_read_contact_access)
//        }
//    }
//
//    override fun getSecondCtaTitle(context: Context): String {
//        return context.getString(com.danish.common.R.string.i_will_do_it_later)
//    }
//}
//class RecordAudioPermissionTextProvider : PermissionCustomTextProvider {
//    override fun getImageResID(): Int? {
//        return null
//    }
//
//    override fun getTitle(context: Context): String {
//        return context.getString(com.danish.common.R.string.microphone_access)
//    }
//
//    override fun getDescription(isPermanentlyDeclined: Boolean, context: Context): String {
//        return if (isPermanentlyDeclined) {
//            context.getString(com.danish.common.R.string.it_seems_you_permanently_declined_microphone_permission_you_can_go_to_the_app_settings_to_grant_it)
//        } else {
//            context.getString(com.danish.common.R.string.this_app_needs_access_to_your_microphone_so_that_your_friends_can_hear_you_in_a_call)
//        }
//    }
//
//    override fun getFirstCtaTitle(isPermanentlyDeclined: Boolean, context: Context): String {
//        return if (isPermanentlyDeclined) {
//            context.getString(com.danish.common.R.string.allow_in_settings)
//        } else {
//            context.getString(com.danish.common.R.string.allow_microphone_access)
//        }
//    }
//
//    override fun getSecondCtaTitle(context: Context): String {
//        return context.getString(com.danish.common.R.string.i_will_do_it_later)
//    }
//}

class AccessFineLocationPermissionTextProvider : PermissionCustomTextProvider {

    override fun getImageResID(): Int {
        return R.drawable.location_circular_icon
    }

    override fun getTitle(context: Context): String {
        return "location_access"
    }

    override fun getDescription(isPermanentlyDeclined: Boolean, context: Context): String {
        return if (isPermanentlyDeclined) {
            ""
        } else {
            ""
        }
    }

    override fun getFirstCtaTitle(isPermanentlyDeclined: Boolean, context: Context): String {
        return if (isPermanentlyDeclined) {
           "allow_in_settings"
        } else {
            "allow_location_access"
        }
    }

    override fun getSecondCtaTitle(context: Context): String {
        return "i_will_do_it_later"
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null),
    ).also(::startActivity)
}
