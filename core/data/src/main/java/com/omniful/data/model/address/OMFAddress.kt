package com.omniful.data.model.address

import com.core.coreDomain.model.address.Address
import java.io.Serializable
import java.util.UUID

data class OMFAddress(
    val address1: String? = null,
    val address2: String? = null,
    val city: String? = null,
    val company: String? = null,
    val country: String? = null,
    val countryCode: String? = null,
    val firstName: String? = null,
    var formatted: List<String> = emptyList(),
    val formattedArea: List<String>? = null,
    val id: String? = null,
    val lastName: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    val name: String? = null,
    val phone: String? = null,
    val province: String? = null,
    val zip: String? = null,
    var countryCodeV2: String? = null,
    var provinceCode: String? = null
) : Serializable, Address {

    fun getID(): String? {
        return id?.substringBefore('?')
    }

    var idNumeric = getID()?.filter { it.isDigit() }

    companion object {


        fun buildWithGeocode(address: android.location.Address): OMFAddress {
            val addressFist: String = address.getAddressLine(0)
            var addressSecond: String = ""
            for (index in 0..address.maxAddressLineIndex) {
                if (index != 0) {
                    addressSecond += address.getAddressLine(index) + " "
                }
            }
            val city: String? = address.locality
            val state: String? = address.adminArea
            val country: String? = address.countryName
            val postalCode: String? = address.postalCode
            val knownName: String? = address.featureName
            val phone: String? = address.phone
            val countryCode: String? = address.countryCode

            return OMFAddress(
                address1 = addressFist,
                address2 = addressSecond,
                city = city,
                phone = phone,
                country = country,
                countryCode = countryCode,
                name = knownName,
                id = UUID.randomUUID().toString(),
                zip = postalCode,
                latitude = address.latitude,
                longitude = address.longitude,
                province = state,
            )
        }
    }

    override fun getLocationId(): String? {
        return id
    }

    override fun getTitle(): String? {
        return name ?: address1 ?: ""
    }

    override fun getSubTitle(): String {
        formattedArea?.let {
            return it.joinToString() ?: ""
        }

        val finalString = "${getTextWithSeparator(address2)}${getTextWithSeparator(city)}${getTextWithSeparator(province)}${country ?: ""}"
        return finalString.trim()
    }

    private fun getTextWithSeparator(text: String?): String {
        return (if (text.isNullOrEmpty()) "" else "$text, ")
    }
}
