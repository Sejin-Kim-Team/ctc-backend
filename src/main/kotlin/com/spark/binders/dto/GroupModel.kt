package com.spark.binders.dto


data class GroupResponse (
    val id: Long,
    val groupName: String,
    val location: String? = null,
    val description: String? = null,
    val groupImage: String? = null,
    val groupMember : MutableList<GroupMemberResponse>? = mutableListOf()
)
