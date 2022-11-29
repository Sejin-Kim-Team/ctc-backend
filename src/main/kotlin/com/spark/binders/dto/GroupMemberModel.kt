package com.spark.binders.dto

import com.spark.binders.domain.entity.GroupMember

data class GroupMemberRequest(
    val groupId : Long,
    val nickname: String?,
)

data class GroupMemberResponse(
    val groupId : Long,
    val groupName : String,
    val nickname: String,
) {
    companion object {
        operator fun invoke(groupMember: GroupMember) {
            GroupMemberResponse(
                groupId = groupMember.group.id!!,
                groupName = groupMember.group.groupName,
                nickname = groupMember.memberNickname!!,
            )
        }
    }
}
