package com.spark.binders.domain.entity

import javax.persistence.*

@Entity
@Table(name = "meeting_member_mapping")
class MeetingMemberMapping(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=null,
    @ManyToOne(fetch = FetchType.LAZY)
    val member: GroupMember,
    @ManyToOne(fetch = FetchType.LAZY)
    val meeting: Meetings,
    var anonymousNickname: String?=null,
    var attendance: Boolean?=false,
) : BaseEntity()