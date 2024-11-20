package com.omniful.data.repository.user

import javax.inject.Inject

class UserMainRepository @Inject constructor(
    private val userRepositoryChain: UserRepositoryChain
) {
    private var userRepository: UserRepository = userRepositoryChain.getRepository()


    fun getName(): String? {
        return userRepository.getName()
    }

}