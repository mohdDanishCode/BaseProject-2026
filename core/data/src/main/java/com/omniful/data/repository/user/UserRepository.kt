package com.omniful.data.repository.user

interface UserRepository {
    fun getName() : String?
    fun setNext(repository: UserRepository): UserRepository
}