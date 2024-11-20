package com.omniful.model.user

object UserDetailFactory {
    fun createUserDetail(userType: UserType, data: Map<String, Any?>): UserDetail {
        return when (userType) {
            UserType.ADMIN -> {
                UserAdmin(
                    name = data["name"] as? String,
                    age = data["age"] as? Int
                )
            }
            UserType.RKB -> {
                UserRKB(
                    name = data["name"] as? String,
                    email = data["email"] as? String
                )
            }
        }
    }
}

enum class UserType {
    ADMIN,
    RKB
}