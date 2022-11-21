package com.spark.binders.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import com.spark.binders.domain.entity.User
import java.time.LocalDateTime

data class UserResponse(
    val userId: String,
    val snsId: String,
    var token: String?=null,
    val name: String,
    val email: String?,
    val createdAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?,
) {
    companion object {
        operator fun invoke(user: User, token: String?) = with(user) {
            UserResponse(
                userId=userId!!,
                snsId=snsId,
                token=token,
                name=name!!,
                email=email,
                createdAt=createdAt,
                modifiedAt = modifiedAt,
            )
        }
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

data class KakaoUserResponse(
    val id: Long,
)