package com.omniful.data.manager.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(interval: Long = 1000L, oneTime: Boolean): Flow<Location>
    fun getCurrentLocation(): Flow<Location>
    class LocationException(message: String) : Exception()
}
