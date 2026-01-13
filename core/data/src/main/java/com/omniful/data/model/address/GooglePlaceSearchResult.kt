package com.core.coreDomain.model.address

import com.google.maps.model.PlacesSearchResult

class GooglePlaceSearchResult(private val placesSearchResult: PlacesSearchResult) : Address {

    override fun getLocationId(): String? {
        return placesSearchResult.placeId
    }

    override fun getTitle(): String? {
        return placesSearchResult.name
    }

    override fun getSubTitle(): String? {
        return placesSearchResult.vicinity
    }

    fun getPlacesSearchResult(): PlacesSearchResult {
        return placesSearchResult
    }
}
