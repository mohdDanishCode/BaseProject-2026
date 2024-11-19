package com.omniful.data.repository.user

import javax.inject.Inject

class UserMainRepository @Inject constructor(
    private val handlers: Set<@JvmSuppressWildcards UserRepository>
) {
    fun getName(): String? {
        return handlers.firstNotNullOfOrNull { it.getName() }
    }

}