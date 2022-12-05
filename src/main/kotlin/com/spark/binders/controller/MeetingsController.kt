package com.spark.binders.controller

import com.spark.binders.dto.MeetingsRequest
import com.spark.binders.service.MeetingsService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller

@Controller
class MeetingsController(
    private val meetingsService: MeetingsService,
) {

    @QueryMapping("getMeetings")
    fun getMeetings(
        @Argument groupId: Long,
        @Argument pageNumber: Int,
        @Argument pageSize: Int
    ) = meetingsService.getMeetings(groupId, pageNumber, pageSize)


    @QueryMapping("getMeeting")
    fun getMeeting(@Argument meetingId: Long) = meetingsService.getMeeting(meetingId)


    @MutationMapping("createMeeting")
    fun createMeeting(@Argument meetingRequest: MeetingsRequest) = meetingsService.createMeeting(meetingRequest)


    @MutationMapping("updateMeeting")
    fun updateMeeting(
        @Argument meetingId: Long,
        @Argument meetingsRequest: MeetingsRequest
    ) = meetingsService.updateMeeting(meetingId, meetingsRequest)


    @MutationMapping("deleteMeeting")
    fun deleteMeeting(@Argument meetingId: Long) = meetingsService.deleteMeeting(meetingId)

    @MutationMapping("joinMeeting")
    fun joinMeeting(
        @Argument meetingId: Long,
        @Argument nickname: String?,
    ) = meetingsService.joinMeeting(meetingId, nickname, getUserId())

    @MutationMapping("leaveMeeting")
    fun leaveMeeting(@Argument meetingId: Long) = meetingsService.leaveMeeting(meetingId, getUserId())

    @MutationMapping("attendance")
    fun attendance(@Argument meetingId: Long) = meetingsService.attendance(meetingId, getUserId())

    private fun getUserId() = SecurityContextHolder.getContext().authentication.name

}