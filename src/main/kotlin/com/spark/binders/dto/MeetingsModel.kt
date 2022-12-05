package com.spark.binders.dto

import com.spark.binders.domain.entity.MeetingMemberMapping
import com.spark.binders.domain.entity.Meetings

data class MeetingsRequest(
    val groupId: Long,
    val meetingName: String? = null,
    val isAnonymous: Boolean? = false,
    val locationInfo: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val limitedMemberCount: Int?= 0 ,
    var joinedMemberCount:Int? = 0,
    val cost: Long?=0,
    val startDate: String,
    val endDate: String?=null,
)

data class MeetingsResponse(
    val id: Long,
    val groupId: Long,
    val groupName: String,
    val meetingName: String? = null,
    val isAnonymous: Boolean? = false,
    val locationInfo: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val limitedMemberCount: Int,
    val joinedMemberCount:Int? = 0,
    val cost: Long? = 0,
    val joinedMembers: MutableList<MeetingMemberResponse>? = mutableListOf(),
    val startDate: String,
    val endDate: String?=null,
) {
    companion object {
        operator fun invoke(meeting : Meetings) = with(meeting) {
            MeetingsResponse(
                id = id!!,
                groupId = meeting.group.id!!,
                groupName = meeting.group.groupName,
                meetingName= meetingName,
                isAnonymous = isAnonymous,
                locationInfo = locationInfo,
                latitude = latitude,
                longitude = longitude,
                limitedMemberCount = limitedMemberCount!!,
                joinedMemberCount = joinedMemberCount,
                cost = meeting.cost,
                joinedMembers = meeting.joinedMembers?.map { MeetingMemberResponse(it) } as MutableList<MeetingMemberResponse>,
                startDate= meeting.startDate.toString(),
                endDate = meeting.endDate.toString()
            )
        }
    }
}
data class MeetingMemberResponse(
    val id: Long,
    val memberId: Long,
    val nickname: String?=null,
    val attendance: Boolean?=false,
) {
    companion object {
        operator fun invoke(meetingMember: MeetingMemberMapping) = with(meetingMember) {
            MeetingMemberResponse(
                id = id!!,
                memberId = member!!.id!!,
                nickname = anonymousNickname,
                attendance = attendance,
            )
        }
    }
}