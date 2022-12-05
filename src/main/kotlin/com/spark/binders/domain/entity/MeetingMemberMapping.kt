package com.spark.binders.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "meeting_member_mapping")
class MeetingMemberMapping(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=null,
    @ManyToOne(fetch = FetchType.LAZY)
    var member: GroupMember?=null,
    @ManyToOne(fetch = FetchType.LAZY)
    var meeting: Meetings?=null,
    var anonymousNickname: String?=null,
    var attendance: Boolean?=false,
) : BaseEntity()