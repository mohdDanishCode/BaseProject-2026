package com.omniful.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Product(
    val id: String,
    val name: String,
    val data: ProductData? = null // Nullable to handle `null` in JSON
)

@Serializable
data class ProductData(
    @SerialName("color") val color: String? = null,
    @SerialName("capacity") val capacity: String? = null,
    @SerialName("capacity GB") val capacityGB: Int? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("generation") val generation: String? = null,
    @SerialName("year") val year: Int? = null,
    @SerialName("CPU model") val cpuModel: String? = null,
    @SerialName("Hard disk size") val hardDiskSize: String? = null,
    @SerialName("Strap Colour") val strapColour: String? = null,
    @SerialName("Case Size") val caseSize: String? = null,
    @SerialName("Color") val alternateColor: String? = null, // Handles "Color" key in Beats Studio3
    @SerialName("Description") val description: String? = null, // Handles "Description" key in Beats Studio3
    @SerialName("Screen size") val screenSize: Double? = null,
    @SerialName("Generation") val generationAlt: String? = null, // Handles "Generation" in iPad Air
    @SerialName("Capacity") val capacityAlt: String? = null // Handles "Capacity" in iPad Mini 5th Gen
)