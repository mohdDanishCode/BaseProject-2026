package com.omniful.data.repository.user

import javax.inject.Inject
import javax.inject.Singleton

class UserCacheRepository @Inject constructor(

):UserRepository {
    private var nextRepository: UserRepository? = null

    override fun getName(): String? {
        return null ?:nextRepository?.getName()
    }

    override fun setNext(repository: UserRepository): UserRepository {
        nextRepository = repository
        return repository
    }
}