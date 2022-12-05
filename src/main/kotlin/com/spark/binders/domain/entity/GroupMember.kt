package com.spark.binders.domain.entity

import jakarta.persistence.*


@Entity
@Table(name = "group_member")
class GroupMember (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user : User? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var group: Group? = null,
    var memberNickname: String? = null,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val joinedMeetings: MutableList<MeetingMemberMapping>? = mutableListOf()
) : BaseEntity() {
    fun joinMeeting(meeting: MeetingMemberMapping) {
        joinedMeetings?.add(meeting)
    }

    fun leaveMeeting(meeting: MeetingMemberMapping) {
        joinedMeetings?.remove(meeting)
        meeting.member = null
    }
}