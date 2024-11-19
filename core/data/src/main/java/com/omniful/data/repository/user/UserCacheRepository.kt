package com.omniful.data.repository.user

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserCacheRepository @Inject constructor():UserRepository {
    override fun getName(): String? {
        return null
    }
}