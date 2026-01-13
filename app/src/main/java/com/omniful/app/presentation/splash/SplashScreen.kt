package com.omniful.app.presentation.splash

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omniful.app.R
import com.omniful.app.presentation.video.VideoPlayerViewModel
import com.omniful.designsystem.components.videoPlayer.PlayerErrorEffect
import com.omniful.data.permission.PermissionUtils
import com.omniful.data.permission.PermissionUtils.locationPermissionsToRequest
import kotlinx.coroutines.delay
import timber.log.Timber


@Composable
fun SplashScreen(onNavigate:()-> Unit) {
    val viewModel = hiltViewModel<SplashViewModel>()
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    var forceUpdate by remember { mutableStateOf(false) }

    var canNavigate by remember {
        mutableStateOf(false)
    }


    val videoPlayerViewModel = hiltViewModel<VideoPlayerViewModel>()
    val videoPlayerManager = remember {
        videoPlayerViewModel.getVideoPlayerManagerById(videoPlayerViewModel.hashCode().toString())
    }

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->

        },
    )

    LaunchedEffect(key1 = Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            multiplePermissionResultLauncher.launch(PermissionUtils.launchPermissionsToRequest)
        }
        else{
            multiplePermissionResultLauncher.launch(locationPermissionsToRequest)
        }
    }

    LaunchedEffect(key1 = true) {
        videoPlayerManager.effect.collect { effect ->
            when (effect) {
                is PlayerErrorEffect -> {
                    canNavigate = true
                }

                else -> {}
            }
        }
    }




    LaunchedEffect(key1 = true) {
        Timber.d("Time Start ->")
        delay(3000)
        Timber.d("Time End ->")
//        splashSetting?.let {
//            canNavigate = splashSetting.mediaType != MediaType.Video
//            return@LaunchedEffect
//        }
        canNavigate = true

    }

    LaunchedEffect( key1 = canNavigate) {
        onNavigate()
    }

//    LaunchedEffect(key1 = initResponse.value, key2 = canNavigate) {
//        if(initResponse.value==null) return@LaunchedEffect
//        if (initResponse.value is ViewState.Success && canNavigate) {
//            NetworkPreference.getInitResponse()?.appSettings?.apply {
//                P1 = getColor(this.primaryColour, 1f)
//            }
//            // Force Update
//            if (viewModel.isUpdateNeeded(context, NetworkPreference.getInitResponse()?.forceUpdate?.android?.minimumVersion?:"0.0")) {
//                forceUpdate = NetworkPreference.getInitResponse()?.forceUpdate?.android?.force?:false
//                showDialog.value = true
//            } else {
//                viewModel.initializeLogs(context)
//                viewModel.logEvent(Event(eventName = EventName.AppLaunched.value))
//                mainViewModel.init()
//                appNavigator.navigateToScreen(Screen.CheckLocationAndServiceScreen, popBackStack = true)
//
//            }
//        }
//    }





    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painterResource(R.drawable.logo),
                modifier = Modifier.size(150.dp),
                contentDescription = "Splash Image",
                contentScale = ContentScale.Fit,
            )

//            if (splashSetting != null) {
//                when (splashSetting.mediaType) {
//                    MediaType.Video -> {
//                        val state by videoPlayerManager.state.collectAsState()
//
//                        DisposableEffect(key1 = Unit) {
//                            if (state.videos.isEmpty()) {
//                                videoPlayerManager.setComponent(
//                                    listOf(
//                                        Component(
//                                            type = ElementType.VIDEO,
//                                            value = ComponentValue(
//                                                null,
//                                                null,
//                                                videoSrc = splashSetting.src
//                                            ),
//                                        ),
//                                    ),
//                                )
//                            }
//                            onDispose {
//                            }
//                        }
//
//                        if (state.videos.isNotEmpty()) {
//                            state.playMediaAt(0, true)
//                            VideoCard(
//                                modifier = Modifier.fillMaxSize(),
//                                player = state.player,
//                                video = state.videos[0],
//                                manager = videoPlayerManager,
//                                showController = false,
//                                onVideoComplete = {
//                                    canNavigate = true
//                                },
//                            ) {
//                            }
//                        }
//                    }
//                    MediaType.Image -> {
//                        ImageGeneric(
//                            modifier = Modifier
//                                .fillMaxSize(),
//                            src = splashSetting.src ?: "local:placeholder",
//                            imageScale = ContentScale.FillBounds,
//                        )
//                    }
//
//                    else -> {}
//                }
//            } else {
//                Image(
//                    painterResource(R.drawable.logo),
//                    modifier = Modifier.size(150.dp),
//                    contentDescription = "Splash Image",
//                    contentScale = ContentScale.Fit,
//                )
//            }
        }
//        if (initResponse.value is ViewState.Error) {
//            PlaceHolderNotFound(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White),
//                placeHolderResId = R.drawable.error_naughty_dog,
//                titleFirst = stringResource(com.danish.common.R.string.uh_oh_an_error_occurred),
//                titleSecond = stringResource(com.danish.common.R.string.we_apologize_but_it_appears_that_something_didn_t_go_as_expected),
//                showButton = true,
//            ) {
//                viewModel.getInitResponse(viewModel.merchantId.value?:context.getString(com.network.core.R.string.merchant_id))
//            }
//        }
//
//        if (showDialog.value) {
//            CustomDialog(
//                canDismiss = false,
//                hideIcon = true,
//                dialogMessage = if(forceUpdate) ForceUpdateDialog(context) else UpdateDialog(
//                    context,
//                ),
//                onClickDelete = {
//                    redirectToPlayStore(context.packageName, context)
////                    showDialog.value = false
//                },
//            ) {
//                showDialog.value = it
//                if(!it){
//                    viewModel.initializeLogs(context)
//                    viewModel.logEvent(Event(eventName = EventName.AppLaunched.value))
//                    appNavigator.navigateToScreen(Screen.CheckLocationAndServiceScreen, popBackStack = true)
//                }
//            }
//        }
    }
}