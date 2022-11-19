package com.spark.binders.dto

import com.spark.binders.domain.entity.User
import java.time.LocalDateTime

data class UserResponse(
    val userId: String,
    val snsId: String,
    val name: String,
    val email: String?,
    val createdAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?,
) {
    companion object {
        operator fun invoke(user: User) = with(user) {
            UserResponse(
                userId=userId!!,
                snsId=snsId,
                name=name!!,
                email=email,
                createdAt=createdAt,
                modifiedAt = modifiedAt,
            )
        }
    }
}