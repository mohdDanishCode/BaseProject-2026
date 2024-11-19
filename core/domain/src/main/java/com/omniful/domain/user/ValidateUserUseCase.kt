package com.omniful.domain.user

class ValidateUserUseCase {
    operator fun invoke(name: String): Boolean {
        return name.isNotEmpty()
    }
}