package com.omniful.data.di.user

import com.omniful.data.repository.user.UserCacheRepository
import com.omniful.data.repository.user.UserDataRepository
import com.omniful.data.repository.user.UserNetworkRepository
import com.omniful.data.repository.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class  UserRepositoryModule {
    @Binds
    internal abstract  fun bindUserCacheRepository(repo: UserCacheRepository): UserRepository

    @Binds
    internal abstract  fun bindUserDataRepository(repo: UserDataRepository): UserRepository

    @Binds
    internal abstract  fun bindUserNetworkRepository(repo: UserNetworkRepository): UserRepository

}