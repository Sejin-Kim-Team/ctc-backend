package com.spark.binders.dto


import com.spark.binders.domain.entity.GroupMember
import com.spark.binders.domain.entity.User
import com.spark.binders.domain.entity.enum.Gender
import java.time.LocalDateTime

data class UserRequest(
    val name: String?,
    val email: String?,
    val joinedGroups: MutableList<GroupMember>? = mutableListOf()
)
data class UserResponse(
    val userId: String,
    val snsId: String,
    var token: String?=null,
    val name: String,
    val email: String?,
    val gender: Gender,
    val joinedGroups: MutableList<GroupMemberResponse>? = mutableListOf(),
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
                gender = gender,
                joinedGroups = user.groupMembers?.map { GroupMemberResponse(it.group.id!!, it.group.groupName, it.memberNickname!!) } as MutableList<GroupMemberResponse>?,
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
                gender= gender,
                joinedGroups = user.groupMembers?.map { GroupMemberResponse(it.group.id!!, it.group.groupName, it.memberNickname!!) } as MutableList<GroupMemberResponse>?,
                createdAt=createdAt,
                modifiedAt = modifiedAt,
            )
        }
    }
}

data class KakaoUserResponse(
    val id: Long,
)
