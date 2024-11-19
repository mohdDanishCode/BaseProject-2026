package com.omniful.datastore.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable


@Serializable
data class UserProfile(
    val name: String = "",
    val knownLocations: PersistentList<Location> = persistentListOf()
)

@Serializable
data class Location(
    val lat: Double,
    val lng: Double
)

