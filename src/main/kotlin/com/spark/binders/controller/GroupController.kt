package com.spark.binders.controller

import com.spark.binders.dto.GroupRequest
import com.spark.binders.service.GroupService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class GroupController(
    private val groupService: GroupService,
) {

    @QueryMapping("getGroups")
    fun getGroups(
        @Argument pageNumber: Int,
        @Argument pageSize: Int
    ) = groupService.getGroups(pageNumber, pageSize)

    @QueryMapping("getGroup")
    fun getGroup(@Argument groupId: Long) = groupService.getGroup(groupId)


    @MutationMapping("createGroup")
    fun createGroup(@Argument("group") groupRequest: GroupRequest) = groupService.createGroup(groupRequest)


    @MutationMapping("updateGroup")
    fun updateGroup(
        @Argument groupId : Long,
        @Argument("group") groupRequest: GroupRequest
    ) = groupService.updateGroup(groupId, groupRequest)


    @MutationMapping("deleteGroup")
    fun deleteGroup(@Argument groupId: Long) = groupService.deleteGroup(groupId)
}