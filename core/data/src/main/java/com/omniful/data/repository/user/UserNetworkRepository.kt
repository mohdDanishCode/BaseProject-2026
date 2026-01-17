package com.omniful.data.repository.user

import com.omniful.database.dao.UserDao
import javax.inject.Inject
import javax.inject.Singleton

class UserNetworkRepository @Inject constructor(
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