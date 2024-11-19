package com.omniful.data.repository.user

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserNetworkRepository @Inject constructor():UserRepository {
    override fun getName(): String? {
        return "Danish"
    }
}