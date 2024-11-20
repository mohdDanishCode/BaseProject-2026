package com.omniful.model.user

class UserAdmin(
    private val name: String?,
    private val age: Int?
) : UserWithAge {
    override fun getName(): String? = name
    override fun getAge(): Int? = age
}