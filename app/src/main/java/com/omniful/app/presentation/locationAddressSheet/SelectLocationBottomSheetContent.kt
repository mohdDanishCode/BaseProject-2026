package com.omniful.app.presentation.locationAddressSheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.coreDomain.model.address.Address
import com.omniful.data.model.address.GoogleAutoCompletePrediction
import com.core.coreDomain.model.address.GooglePlaceSearchResult
import com.omniful.data.model.address.OMFAddress
import com.core.coreDomain.model.address.OMFLatLng
import com.omniful.app.presentation.permission.LocationPermissionDialog
import com.omniful.app.presentation.locationAddressSheet.components.CurrentLocationButton
import com.omniful.app.presentation.locationAddressSheet.components.ItemEnableLocation
import com.omniful.app.presentation.locationAddressSheet.components.ItemLocation
import com.omniful.app.routes.LocalAppNavigator
import com.omniful.app.presentation.permission.PermissionViewModel
import com.omniful.common.result.ViewState
import com.omniful.data.manager.language.LanguageUtil.findActivity
import com.omniful.data.permission.PermissionUtils.hasAccessFineLocationPermission
import com.omniful.designsystem.components.onClickWithHaptics
import com.omniful.designsystem.theme.Grey300
import com.omniful.designsystem.theme.PandaSearchInputField
import com.omniful.designsystem.theme.RememberLifecycleEvent
import kotlinx.coroutines.launch

@Composable
fun SelectLocationBottomSheetContent(
    viewModel: AddressBottomSheetViewModel = hiltViewModel(),
    onCLickNearByOrSearchedAddress: (OMFLatLng) -> Unit
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val lifecycleEvent = RememberLifecycleEvent()

    var checkPermission by rememberSaveable {
        mutableStateOf(true)
    }
    var askForPermissionCount by rememberSaveable {
        mutableIntStateOf(0)
    }
    val showDialog = remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val selectedScreen by viewModel.selectedScreen.observeAsState()

    val nearbyAddress: ViewState<List<Address>> by viewModel.nearbyDisplayAddress.collectAsStateWithLifecycle()
    val searchAddress: ViewState<List<Address>> by viewModel.searchDisplayAddress.collectAsStateWithLifecycle()


    val appNavigator = LocalAppNavigator.current

    var showLoading by remember {
        mutableStateOf(false)
    }

    val searchText by remember {
        derivedStateOf {
            TextFieldValue(
                text = viewModel.searchText.value,
                selection = TextRange(viewModel.searchText.value.length),
            )
        }
    }



    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            checkPermission = context.hasAccessFineLocationPermission()
        }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                focusManager.clearFocus()
                return Offset.Zero
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Text(
                text = if (viewModel.getInitialScreen() == AddressBottomSheetViewModel.Companion.BottomSheetViewState.SHOW_NEARBY_ADDRESS) {
                    "select location"
                } else {
                    "select location"
                },
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 18.dp, end = 16.dp),
            )


            PandaSearchInputField(
                modifier = Modifier.padding(horizontal = 16.dp),
                onValueChange = { query ->
                    viewModel.onSearch(query)
                }
            )

            when (selectedScreen) {
                AddressBottomSheetViewModel.Companion.BottomSheetViewState.SHOW_SAVED_ADDRESS.name -> {

                }
                AddressBottomSheetViewModel.Companion.BottomSheetViewState.SHOW_SEARCH_ADDRESS.name -> {
                    SearchAddressScreen(
                        nestedScrollConnection,
                        modifier = Modifier.weight(1f),
                        searchAddress = searchAddress,
                        showDialog = showDialog,
                        onCLickNearByOrSearchedAddress = {
                            onCLickNearByOrSearchedAddress(it)
                        },
                    )
                }
                AddressBottomSheetViewModel.Companion.BottomSheetViewState.SHOW_NEARBY_ADDRESS.name -> {
                    NearbyAddressScreen(
                        nestedScrollConnection = nestedScrollConnection,
                        modifier = Modifier.weight(1f),
                        checkPermission = checkPermission,
                        nearByAddress = nearbyAddress,
                        askForPermission = {
                            askForPermissionCount++
                        },
                        onCLickNearByOrSearchedAddress = {
                            onCLickNearByOrSearchedAddress(it)
                        },
                    )
                }

                AddressBottomSheetViewModel.Companion.BottomSheetViewState.SHOW_TRY_AGAIN.name -> {
                }
            }
        }

        if (showLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        if (showDialog.value) {
//            CustomDialog(
//                dialogMessage = AddressDeleteDialogMessage(
//                    context,
//                    highlightedDynamicText = viewModel.itemToDelete?.getTitle(),
//                ),
//                onClickDelete = {
//                    showDialog.value = false
//                    viewModel.delAddress()
//                },
//            ) {
//                viewModel.itemToDelete = null
//                showDialog.value = it
//            }
        }

        LocationPermissionDialog(
            activity = activity,
            askForPermissionCount = askForPermissionCount,
            onGranted = {
            },
            onDenied = {
            },
        )
    }
}


@Composable
fun SearchAddressScreen(
    nestedScrollConnection: NestedScrollConnection,
    modifier: Modifier = Modifier,
    showDialog: MutableState<Boolean>,
    viewModel: AddressBottomSheetViewModel = hiltViewModel(),
    searchAddress: ViewState<List<Address>>,
    onCLickNearByOrSearchedAddress: (OMFLatLng) -> Unit,

) {
    val coroutineScope = rememberCoroutineScope()
    val appNavigator = LocalAppNavigator.current

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (searchAddress is ViewState.Success) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .nestedScroll(nestedScrollConnection),
            ) {
                val data = (searchAddress).data ?: arrayListOf()
                if (data.isNotEmpty()) {
                    LazyColumn {
                        items(data.size, key = { data[it].getLocationId()!! }) { index ->
                            ItemLocation(
                                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                title = data[index].getTitle(),
                                subTitle =data[index].getSubTitle()
                            ) {
                                val address = data[index]
                                if (address is GoogleAutoCompletePrediction) {
                                    // FIXME:
                                    viewModel.locationManager.getPlaceDetail(
                                        address.getAutocompletePrediction().placeId,
                                        {
                                            it.place.latLng?.let { latLong ->
                                                coroutineScope.launch {
                                                    onCLickNearByOrSearchedAddress(
                                                        OMFLatLng(
                                                            latitude = latLong.latitude,
                                                            longitude = latLong.longitude,
                                                        ),
                                                    )
                                                }
                                            }
                                        },
                                        {
                                        },
                                    )
                                }
                                if (address is OMFAddress) {
//                                    onClickToSavedAddress(address)
                                }
                            }
                        }
                    }
                } else {
//                    PlaceHolderNotFound(
//                        modifier = modifier.fillMaxSize(),
//                        placeHolderResId = R.drawable.no_result_icon,
//                        titleFirst = "${stringResource(com.danish.common.R.string.apologies_we_couldn_t_find)} “${viewModel.searchText.value}“",
//                        titleSecond = stringResource(com.danish.common.R.string.please_ensure_that_you_have_entered_the_correct_location_name_or_keywords),
//                    ) {}
                }
            }
        } else if (searchAddress is ViewState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
//            PlaceHolderNotFound(
//                modifier = modifier.fillMaxSize(),
//                placeHolderResId = R.drawable.no_result_icon,
//                titleFirst = "${stringResource(com.danish.common.R.string.apologies_we_couldn_t_find)} “${viewModel.searchText.value}“",
//                titleSecond = stringResource(com.danish.common.R.string.please_ensure_that_you_have_entered_the_correct_location_name_or_keywords),
//            ) {
//            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NearbyAddressScreen(
    nestedScrollConnection: NestedScrollConnection,
    modifier: Modifier = Modifier,
    viewModel: AddressBottomSheetViewModel = hiltViewModel(),
    nearByAddress: ViewState<List<Address>>,
    permissionViewModel: PermissionViewModel = hiltViewModel(),
    checkPermission: Boolean,
    askForPermission: () -> Unit,
    onCLickNearByOrSearchedAddress: (OMFLatLng) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val showLoading = rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection),
    ) {
        Column(
            modifier = modifier,
        ) {
            LazyColumn {
                item {
                    if (checkPermission) {
                        CurrentLocationButton(
                            text = "use_my_current_location",
                            modifier = Modifier
                                .padding(horizontal = 5.3.dp, vertical = 9.3.dp)
                                .fillMaxWidth(),
                        ) {
                            showLoading.value = true
                            permissionViewModel.getCurrentLocationAndAddress {
                                onCLickNearByOrSearchedAddress(it)
                                showLoading.value = false
                            }
                        }
                    } else {
                        ItemEnableLocation(
                            modifier = Modifier.onClickWithHaptics {
                                askForPermission()
                            },
                        )
                    }

                    Divider(thickness = 1.dp, color = Grey300)
                }

                if (nearByAddress is ViewState.Success) {
                    val data = (nearByAddress).data ?: arrayListOf()

                    stickyHeader {
                        if (data.isNotEmpty()) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .padding(horizontal = 16.dp)
                                    .padding(top = 16.dp, bottom = 8.dp),
                                text = "nearby_address",

                            )
                        }
                    }
                    items(data.size, key = { data[it].getLocationId()!! }) { index ->
                        ItemLocation(
                            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
                            title = data[index].getTitle(),
                            subTitle =data[index].getSubTitle()
                        ) {
                            val address = data[index]
                            if (address is GooglePlaceSearchResult) {
                                viewModel.locationManager.getPlaceDetail(
                                    address.getPlacesSearchResult().placeId,
                                    {
                                        it.place.latLng?.let { latLong ->
                                            coroutineScope.launch {
                                                onCLickNearByOrSearchedAddress(
                                                    OMFLatLng(
                                                        latitude = latLong.latitude,
                                                        longitude = latLong.longitude,
                                                    ),
                                                )
                                            }
                                        }
                                    },
                                    {
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
        if (nearByAddress is ViewState.Loading || showLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
