package com.omniful.model.user

class UserRKB(
    private val name: String?,
    private val email: String?
) : UserWithEmail ,UserWithExtraFinger{
    override fun getName(): String? = name
    override fun getEmail(): String? = email
    override fun getExtraFinger(): String? {
        return "finger"
    }
}