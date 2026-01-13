package com.omniful.data.manager.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Geocoder.GeocodeListener
import android.os.Build
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.core.coreDomain.model.address.GooglePlaceSearchResult
import com.core.coreDomain.model.address.OMFLatLng
import com.core.coreDomain.model.address.OMFLatLng.Companion.COUNTRY_ISO
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.*
import com.google.maps.GeoApiContext
import com.google.maps.PlacesApi
import com.google.maps.model.LatLng
import com.google.maps.model.PlacesSearchResponse
import com.google.maps.model.RankBy
import com.omniful.common.network.Dispatcher
import com.omniful.common.network.OmnifulDispatchers
import com.omniful.common.result.ViewState
import com.omniful.data.manager.language.LanguageUtil
import com.omniful.data.model.address.GoogleAutoCompletePrediction
import com.omniful.data.model.address.OMFAddress
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.invoke
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.text.get

@Singleton
class LocationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(OmnifulDispatchers.Default) private val dispatchers: CoroutineDispatcher
) {
    var locationClient: LocationClient = DefaultLocationClient(
        context,
        LocationServices.getFusedLocationProviderClient(context),
    )

//    private var currentLatLng: MutableLiveData<LiveDataEvent<ViewState<com.core.core_domain.model.address.CurrentLatLng>>> = MutableLiveData()
//    var currentAddress: MutableLiveData<LiveDataEvent<ViewState<AddressData>>> = MutableLiveData()
    var allAddress = MutableStateFlow<ViewState<SnapshotStateList<OMFAddress>>>(ViewState.Loading)
    private var allAddressArrayList = arrayListOf<OMFAddress>()

    var postAddressResponse = MutableSharedFlow<ViewState<OMFAddress>>()

//    var getAddressByIdResponse = MutableSharedFlow<ViewState<PostAddressResponse>>()
    var editAddressResponse = MutableSharedFlow<ViewState<OMFAddress>>()
    var deleteAddressResponse = MutableSharedFlow<ViewState<String>>()
    var nearByAddress = MutableStateFlow<ViewState<List<GooglePlaceSearchResult>>>(ViewState.Loading)

    var setService = MutableStateFlow<ViewState<Boolean>?>(null)
    
    private val apiKey: String by lazy {
//        NetworkPreference.getInitResponse()?.integrations?.filter { it.isEnabled && it.identifier == IntegrationIdentifier.GOOGLE_PLACES }?.firstOrNull()?.let {
//            it.apiKey.toString()
//        }?:context.getString(com.network.core.R.string.google_maps_api_key)
        "AIzaSyCO2q_jblqq9AYwsBNfBHNotEDMRQKo-9k"
    }
    private val placesClient: PlacesClient? by lazy {
        if(apiKey.trim().isNotEmpty()){
            if (!Places.isInitialized()) {
                Places.initialize(context, apiKey)
            }
            Places.createClient(context)
        }
        else{
            null
        }

    }
    private val geoApiContext: GeoApiContext ? by lazy {
        if(apiKey.trim().isNotEmpty()){
            GeoApiContext.Builder()
                .apiKey(apiKey)
                .build()
        }
        else{
            null
        }
    }


    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ -> }


    /***
     * GET ADDRESS FROM  LAT LONG ** NOV 2023
     */
    suspend fun getAddressFromLatLong(lat: Double, long: Double): OMFAddress {
        val geocoder = Geocoder(context, Locale(LanguageUtil.getSavedLanguage(context)))
        return suspendCoroutine { continuation ->

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(
                    lat,
                    long,
                    1,
                    object : GeocodeListener {
                        override fun onGeocode(addresses: MutableList<Address>) {
                            val address = addresses.firstOrNull()
                            if (address != null) {
                                continuation.resumeWith(Result.success(OMFAddress.buildWithGeocode(address)))
                            } else {
                                continuation.resumeWithException(Exception("No Address Found"))
                            }
                        }

                        override fun onError(errorMessage: String?) {
                            super.onError(errorMessage)
                            continuation.resumeWithException(Exception(errorMessage))
                        }
                    },
                )
            } else {
                val address = geocoder.getFromLocation(lat, long, 1)?.firstOrNull()
                if (address != null) {
                    continuation.resumeWith(Result.success(OMFAddress.buildWithGeocode(address)))
                } else {
                    continuation.resumeWithException(Exception("No Address Found"))
                }
            }
        }
    }

    /***
     * GET LAT LONG From CountryCode ** NOV 2023
     */
    suspend fun getLatLngFromCountryCode(countryCode: List<String>): OMFLatLng? {
        for (countryCodeItem in countryCode) {
            if (COUNTRY_ISO[countryCodeItem.uppercase()]!=null) {
                return COUNTRY_ISO[countryCodeItem.uppercase()]
            }
        }
        return COUNTRY_ISO["IN"]
    }

    /***
     * GET LAT LONG From Zipcode ** NOV 2023
     */
    suspend fun getLatLngFromAddress(address: OMFAddress): OMFLatLng {
        return suspendCancellableCoroutine { continuation ->
            val geocoder = Geocoder(context, Locale(LanguageUtil.getSavedLanguage(context)))

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocationName(address.getTitle()+address.getSubTitle(), 1,object :GeocodeListener{
                        override fun onGeocode(addresses: MutableList<Address>) {
                            if (addresses.isNotEmpty()) {
                                val latitude = addresses[0].latitude
                                val longitude = addresses[0].longitude
                                continuation.resumeWith(Result.success(OMFLatLng(latitude, longitude)))
                            }
                            else{
                                continuation.resumeWith(Result.success(OMFLatLng(0.0, 0.0)))
                            }
                        }

                        override fun onError(errorMessage: String?) {
                            super.onError(errorMessage)
                            continuation.resumeWith(Result.success(OMFLatLng(0.0, 0.0)))
                        }
                    })
                }
                else{
                    val addresses = geocoder.getFromLocationName(address.getSubTitle(), 1)
                    if (!addresses.isNullOrEmpty()) {
                        val latitude = addresses[0].latitude
                        val longitude = addresses[0].longitude
                        continuation.resumeWith(Result.success(OMFLatLng(latitude, longitude)))
                    }
                    else{
                        continuation.resumeWith(Result.success(OMFLatLng(0.0, 0.0)))
                    }
                }

            } catch (e: Exception) {
                continuation.resumeWith(Result.success(OMFLatLng(0.0, 0.0)))
            }
        }

    }

    /***
     * GET ONLY CURRENT LAT LONG
     * FIXME
     */
    fun getCurrentLocation(latLng: (OMFLatLng) -> Unit, onGetError: (String) -> Unit) {
        CoroutineScope(dispatchers + coroutineExceptionHandler).launch {
            locationClient
                .getCurrentLocation()
                .catch { e ->
                    Timber.e(e.message.toString())
                    onGetError(e.message.toString())
                }
                .onEach { location ->
                    val lat = location.latitude.toString()
                    val long = location.longitude.toString()
                    Timber.d("Location: ($lat, $long)")
                    val currentLatLngValue = OMFLatLng(
                        latitude = location.latitude,
                        longitude = location.longitude,
                    )
                    latLng(currentLatLngValue)
                    //                    AppPreference.setCurrentLatLng(currentLatLngValue) // Save Lat long to App Preference
                }.launchIn(this)
        }
    }



    /***
     * GET ADDRESS FROM LAT LONG WITH GEOCODE
     */
    fun getAddressFromLatLong(lat: Double, long: Double, onError: (String) -> Unit, addressCallback: (OMFAddress?) -> Unit) {
        Geocoder(context, Locale(LanguageUtil.getSavedLanguage(context))).getAddress(
            lat,
            long,
            { address: Address? ->
                addressCallback(address?.let { OMFAddress.buildWithGeocode(it) })
            },
            { error ->
                onError(error)
            },
        )
    }

    /***
     * GET ADDRESS FROM LAT LONG WITH GEOCODE
     */
    private fun Geocoder.getAddress(
        latitude: Double,
        longitude: Double,
        address: (Address?) -> Unit,
        onGetError: (String) -> Unit,
    ) {
        Timber.d("getAddress Location: ($latitude, $longitude)")
        val geocoderJob = CoroutineScope(dispatchers + coroutineExceptionHandler).launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getFromLocation(
                    latitude,
                    longitude,
                    1,
                    object : GeocodeListener {
                        override fun onGeocode(addresses: MutableList<Address>) {
                            address(addresses.firstOrNull())
                        }

                        override fun onError(errorMessage: String?) {
                            super.onError(errorMessage)
                            onGetError(errorMessage.toString())
                        }
                    },
                )
            } else {
                address(getFromLocation(latitude, longitude, 1)?.firstOrNull())
            }
        }

        geocoderJob.invokeOnCompletion {
            if (it != null) {
                onGetError(it.message.toString())
            }
        }
    }

    /***
     * GET PLACE DETAIL WITH PLACE ID
     */
    fun getPlaceDetail(placeId: String, placeResponse: (FetchPlaceResponse) -> Unit, onGetError: (String) -> Unit) {
        val placeFields = listOf(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS_COMPONENTS,
            Place.Field.LAT_LNG,
            Place.Field.ADDRESS,
            Place.Field.PHONE_NUMBER,
        )
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)
        placesClient?.fetchPlace(request)
            ?.addOnSuccessListener { response: FetchPlaceResponse ->
                placeResponse(response)
            }?.addOnFailureListener { exception: Exception ->
                onGetError(exception.message.toString())
            }?: onGetError("No Address")
    }

    /***
     * POST NEARBY ADDRESS FROM PROVIDED to LIVE DATA
     */
    fun postNearByAddress(result: List<GooglePlaceSearchResult>) {
        CoroutineScope(dispatchers + coroutineExceptionHandler).launch {
            nearByAddress.emit(ViewState.Success(result))
        }
    }

    /***
     * GET NEARBY ADDRESS WITH GOOGLE API
     */
    fun getNearByAddress(latitude: Double, longitude: Double) {
        Timber.d("Get NearBy Address with latitude $latitude and longitude $longitude")
        val nearByAddressJob = CoroutineScope(dispatchers + coroutineExceptionHandler).launch {
            nearByAddress.emit(ViewState.Loading)

            var request = PlacesSearchResponse()

            val context: GeoApiContext = geoApiContext?:throw Exception("No Address")
            val location = LatLng(latitude, longitude)
            request = PlacesApi.nearbySearchQuery(context, location)
                .radius(5000)
                .rankby(RankBy.PROMINENCE)
                //                .keyword("cruise")
                .language(LanguageUtil.getSavedLanguage(this@LocationManager.context))
                //                .type(PlaceType.RESTAURANT)
                .await()

            val googlePlaceSearchResult = request.results.map {
                GooglePlaceSearchResult(it)
            }

            Timber.d("Get NearBy Address Success ${googlePlaceSearchResult}")
            postNearByAddress(googlePlaceSearchResult)
        }
        nearByAddressJob.invokeOnCompletion {
            if (it != null) {
                Timber.d("Get NearBy Address Error ${it}")
                CoroutineScope(dispatchers + coroutineExceptionHandler).launch {
                    nearByAddress.emit(ViewState.Error(it.message))
                }
            }
        }
    }

    /***
     * SEARCH ADDRESS WITH GOOGLE API
     */
    suspend fun searchAddress(string: String, predictionList: suspend (List<AutocompletePrediction>) -> Unit, onGetError: (String) -> Unit) {
        val result = suspendCoroutine<List<AutocompletePrediction>> { continuation ->
            val token = AutocompleteSessionToken.newInstance()
            val request = FindAutocompletePredictionsRequest.builder() // Call either setLocationBias() OR setLocationRestriction().
//                .setCountry("in")
                .setSessionToken(token)
                .setQuery(string)
                .build()

            placesClient?.findAutocompletePredictions(request)
                ?.addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                    continuation.resumeWith(Result.success(response.autocompletePredictions))
                }
                ?.addOnFailureListener { exception: Exception ->
                    if (exception is ApiException) {
                        val apiException: ApiException = exception
                    }
                    onGetError(exception.message.toString())
                    continuation.resumeWithException(exception)
                }?:  continuation.resumeWith(Result.success(listOf()))

        }
        predictionList(result)
    }

    /***
     * SEARCH ADDRESS WITH GOOGLE API AND OMNIFUL ADDRESS
     */
    suspend fun searchFromAllAddresses( text: String, result: suspend (ViewState<List<com.core.coreDomain.model.address.Address>>) -> Unit) {
        coroutineScope {
            result(ViewState.Loading)
            val searchItem = arrayListOf<com.core.coreDomain.model.address.Address>()
//            val addressJob = launch {
//                allAddress.forEach { address ->
//                    if (address.address1?.lowercase()?.contains(text.lowercase()) == true
////                        || address.address_type?.name?.lowercase()?.contains(text.lowercase()) == true
//                    ) {
//                        searchItem.add(
//                            0,
//                            address,
//                        )
//                    }
//                }
//            }
            val searchJob = launch {
                searchAddress(
                    text,
                    { predictiveSearchList ->
                        predictiveSearchList.forEach {
                            searchItem.add(GoogleAutoCompletePrediction(it))
                        }
                    },
                    {
                        CoroutineScope(Dispatchers.IO).launch {
                            result(ViewState.Error(it))
                        }
                    },
                )
            }
//            addressJob.join()
            searchJob.join()
            result(ViewState.Success(searchItem))
        }
    }



}
