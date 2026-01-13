package com.omniful.data.model.address

import com.core.coreDomain.model.address.Address
import com.google.android.libraries.places.api.model.AutocompletePrediction

class GoogleAutoCompletePrediction(private val autocompletePrediction: AutocompletePrediction) :
    Address {

    override fun getLocationId(): String? {
        return autocompletePrediction.placeId
    }

    override fun getTitle(): String {
        return autocompletePrediction.getPrimaryText(null).toString()
    }

    override fun getSubTitle(): String {
        return autocompletePrediction.getSecondaryText(null).toString()
    }

    fun getAutocompletePrediction(): AutocompletePrediction {
        return autocompletePrediction
    }
}
