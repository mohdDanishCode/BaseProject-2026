package com.omniful.model.user



interface UserWithExtraFinger{
    fun getExtraFinger(): String?
}

interface UserDetail {
     fun getName(): String?
}

interface UserWithEmail:UserDetail{
    fun getEmail(): String?
}

interface UserWithAge:UserDetail{
    fun getAge(): Int?
}