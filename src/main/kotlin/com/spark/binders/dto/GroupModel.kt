package com.spark.binders.dto

import com.spark.binders.domain.entity.Group


data class GroupRequest(
    val name : String?,
    val location: String? = null,
    val locationDetail : String?=null,
    val description: String? = null,
    val imageUrl: String? = null,
    val groupMember: MutableList<GroupMemberResponse>? = mutableListOf(),
)
data class GroupResponse (
    val id: Long,
    val name: String,
    val location: String? = null,
    val locationDetail: String?=null,
    val description: String? = null,
    val imageUrl: String? = null,
    val members : MutableList<GroupMemberResponse>? = mutableListOf()
) {
    companion object {
        operator fun invoke(group: Group) = with(group) {
            GroupResponse(
                id=id!!,
                name = groupName,
                location = location,
                locationDetail = locationDetail,
                description = description,
                imageUrl = groupImage,
                members = groupMembers?.map { GroupMemberResponse(id, groupName, it.memberNickname!!) } as MutableList<GroupMemberResponse>?
            )
        }
    }
}
