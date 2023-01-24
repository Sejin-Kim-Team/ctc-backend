package com.spark.binders.domain.entity

import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "meetings")
class Meetings(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=null,
    @ManyToOne(fetch = FetchType.EAGER)
    val group: Group,
    var meetingName: String? = null,
    var isAnonymous: Boolean? = false,
    var locationInfo: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var limitedMemberCount: Int? = 0,
    var joinedMemberCount:Int? = 0,
    var cost: Long?=0,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "meeting")
    val joinedMembers : MutableList<MeetingMemberMapping>? = mutableListOf(),
    var startDate: LocalDateTime? = null,
    var endDate: LocalDateTime? = null,
) : BaseEntity() {
    fun joinMeeting(memberMapping: MeetingMemberMapping) {
        joinedMembers?.add(memberMapping)
    }

    fun leaveMeeting(memberMapping: MeetingMemberMapping) {
        joinedMembers?.remove(memberMapping)
        memberMapping.meeting = null
    }
}