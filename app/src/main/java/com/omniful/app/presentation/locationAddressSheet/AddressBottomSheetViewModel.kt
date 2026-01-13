package com.omniful.app.presentation.locationAddressSheet

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.coreDomain.model.address.Address
import com.core.coreDomain.model.address.GooglePlaceSearchResult
import com.core.coreDomain.model.address.OMFLatLng
import com.omniful.common.network.Dispatcher
import com.omniful.common.network.OmnifulDispatchers
import com.omniful.common.result.ViewState
import com.omniful.data.manager.location.LocationManager
import com.omniful.data.model.address.OMFAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressBottomSheetViewModel @Inject constructor(
    application: Application,
    var locationManager: LocationManager,
    @Dispatcher(OmnifulDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    var selectedScreen = MutableLiveData<String>()
    var nearByAddress: ArrayList<GooglePlaceSearchResult> = arrayListOf()

    private var mScreen: BottomSheetViewState ? = null
    private var searchJob: Job? = null
    private var mSelectedLatLong: OMFLatLng? = null
    private var searchAddress = MutableSharedFlow<ViewState<List<Address>>>()
    private var coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable -> }
    var searchText = mutableStateOf("")

    val searchDisplayAddress = MutableStateFlow<ViewState<List<Address>>>(ViewState.Loading)
    val nearbyDisplayAddress = MutableStateFlow<ViewState<List<Address>>>(ViewState.Loading)

    var setService = locationManager.setService

    var deleteAddress = locationManager.deleteAddressResponse
    var getLatLngFromAddressResponse = MutableSharedFlow<ViewState<OMFAddress>>()

    fun getInitialScreen(): BottomSheetViewState? {
        return mScreen
    }

    init {
        viewModelScope.launch {
            locationManager.nearByAddress.collectLatest {

                val response = when (it) {
                    is ViewState.Success -> {
                        nearByAddress.clear()
                        nearByAddress.addAll((it).data ?: emptyList())
                        val listTemp = (it).data?.map {
                            it
                        }
                        ViewState.Success(listTemp)
                    }

                    is ViewState.Error -> {
                        ViewState.Error(it.message)
                    }

                    else -> {
                        ViewState.Loading
                    }
                }
                nearbyDisplayAddress.emit(response)
            }
        }



        viewModelScope.launch {
            searchAddress.collectLatest {
                selectedScreen.value = BottomSheetViewState.SHOW_SEARCH_ADDRESS.name
                searchDisplayAddress.emit(it)
            }
        }
    }


    fun setInitialScreen(
        screen: BottomSheetViewState,
        selectedLatLong: OMFLatLng? ,
//        selectedLatLong: OMFLatLng? = OMFLatLng(AppPreference.getSelectedAddress()?.latitude ?: 0.0, AppPreference.getSelectedAddress()?.longitude ?: 0.0),
        text: String = "",
    ) {
        viewModelScope.launch {
//            savedDisplayAddress.emit(ViewState.Loading)
            searchDisplayAddress.emit(ViewState.Loading)
            nearbyDisplayAddress.emit(ViewState.Loading)
            this@AddressBottomSheetViewModel.mScreen = screen
            this@AddressBottomSheetViewModel.mSelectedLatLong = selectedLatLong
            this@AddressBottomSheetViewModel.searchText.value = text
            nearByAddress.clear()
            onSearch(text)
        }
    }

    private fun setInitialScreenState() {
        if (mScreen == null) return
        selectedScreen.value = mScreen!!.name
        if (mScreen!!.name == Companion.BottomSheetViewState.SHOW_SAVED_ADDRESS.name) {
            if (nearByAddress.isEmpty()) {
                mSelectedLatLong?.let {
                    locationManager.getNearByAddress(it.latitude, it.longitude)
                }
            } else {
                locationManager.postNearByAddress(ArrayList(nearByAddress))
            }
        } else {
            if (nearByAddress.isEmpty()) {
                mSelectedLatLong?.let {
                    locationManager.getNearByAddress(it.latitude, it.longitude)
                }
            } else {
                locationManager.postNearByAddress(ArrayList(nearByAddress))
            }
        }
    }

    fun onReset() {
    }
    override fun onCleared() {
        onReset()
        super.onCleared()
    }



    fun onSearch(text: CharSequence) {
        searchText.value = text.toString()
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            if (!text.isNullOrEmpty() && text.length > 2) {
                onSearchAddressFromAll(text.toString())
            } else {
                searchAddress.emit(ViewState.Success(arrayListOf()))
                setInitialScreenState()
            }
        }
    }

    private fun onSearchAddressFromAll(text: String) {
        viewModelScope.launch(ioDispatcher + coroutineExceptionHandler) {
            locationManager.searchFromAllAddresses( text) {
                searchAddress.emit(it)
            }
        }
    }



    companion object {
        enum class BottomSheetViewState {
            SHOW_SAVED_ADDRESS,
            SHOW_NEARBY_ADDRESS,
            SHOW_SEARCH_ADDRESS,
            SHOW_TRY_AGAIN,
        }
    }
}
