package com.spark.binders.service

import com.spark.binders.domain.entity.Group
import com.spark.binders.domain.repository.GroupRepository
import com.spark.binders.dto.GroupRequest
import com.spark.binders.dto.GroupResponse
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
@Transactional
class GroupService (
    private val groupRepository: GroupRepository,
){

    private val log = KotlinLogging.logger {  }
    fun createGroup(groupRequest: GroupRequest) : GroupResponse {
        return with(groupRequest) {
            val group = Group(
                groupName = name!!,
                location = location,
                locationDetail = locationDetail,
                description = description,
                groupImage = imageUrl,
                groupMembers = mutableListOf(),
                meetings = mutableListOf(),
            )
            GroupResponse(groupRepository.save(group))
        }
    }

    fun getGroups(pageNumber: Int, pageSize: Int) : List<GroupResponse> {
        val pageable = PageRequest.of(pageNumber, pageSize)
        val groups = groupRepository.findAll(pageable).toList()

        return groups.map { GroupResponse(it) }
    }

    fun getGroup(groupId : Long): GroupResponse {
        val group = groupRepository.findWithGroupMembersById(groupId) ?: throw RuntimeException()

        log.info { "MEMBERS !!! : ${group.groupMembers?.get(0)?.memberNickname}" }
        return GroupResponse(group)
    }

    fun updateGroup(groupId: Long, groupRequest: GroupRequest) : GroupResponse {
        val group = groupRepository.findByIdOrNull(groupId) ?: throw RuntimeException()

        return with(group) {
            groupName = groupRequest.name ?: groupName
            location = groupRequest.location ?: location
            locationDetail = groupRequest.locationDetail ?: locationDetail
            description = groupRequest.description ?: description
            groupImage = groupRequest.imageUrl ?: groupImage

            GroupResponse(groupRepository.save(this))
        }
    }

    fun deleteGroup(groupId: Long) : Boolean {
        groupRepository.deleteById(groupId)
        return true
    }
}