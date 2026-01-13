package com.core.coreDomain.model.address

data class OMFHub(
    var name: String?,
    var id: String,
    var formattedAddress: List<String> = arrayListOf(),
    var lat: Double?,
    var long: Double?,
    var phoneNumber: String?,
) {
    fun getFormattedAddress(): String {
        return formattedAddress.joinToString(separator = ", ")
    }
}
