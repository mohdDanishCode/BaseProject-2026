package com.omniful.data.repository.user

import javax.inject.Inject


class UserRepositoryChain @Inject constructor(
    userCacheRepository: UserCacheRepository,
    userDataRepository: UserDataRepository,
    userNetworkRepository: UserNetworkRepository
) {

    private val repoList = listOf(userCacheRepository,userDataRepository,userNetworkRepository)

    fun getRepository():UserRepository{
        for (i in 0 until repoList.size - 1) {
            repoList[i].setNext(repoList[i + 1])
        }
        return repoList.first()
    }

}