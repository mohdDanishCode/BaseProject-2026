package com.omniful.data.repository.user

import com.omniful.database.dao.UserDao
import javax.inject.Inject
import javax.inject.Singleton

class UserDataRepository @Inject constructor(
    dao: UserDao
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