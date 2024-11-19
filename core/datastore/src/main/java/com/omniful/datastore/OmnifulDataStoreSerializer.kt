package com.omniful.datastore

import androidx.datastore.core.Serializer
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.io.InputStream
import java.io.OutputStream

object OmnifulDataStoreSerializer {
    // A function that provides a generic serializer for any object and accepts a default value
    @OptIn(InternalSerializationApi::class)
    inline fun <reified T : Any> createSerializer(defaultValue: T): Serializer<T> {
        return object : Serializer<T> {

            // The default value is passed during serializer creation
            override val defaultValue: T = defaultValue

            override suspend fun readFrom(input: InputStream): T {
                return try {
                    // Try to deserialize from the input stream
                    Json.decodeFromString(T::class.serializer(), input.readBytes().decodeToString())
                } catch (e: SerializationException) {
                    e.printStackTrace()
                    defaultValue  // Return the passed default value if deserialization fails
                }
            }

            override suspend fun writeTo(t: T, output: OutputStream) {
                // Convert the object to a JSON string and write it to the output stream
                val jsonString = Json.encodeToString(T::class.serializer(), t)
                output.write(jsonString.encodeToByteArray())
            }
        }
    }
}