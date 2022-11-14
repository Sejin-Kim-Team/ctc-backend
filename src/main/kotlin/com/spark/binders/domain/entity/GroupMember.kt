package com.spark.binders.domain.entity

import javax.persistence.*

@Entity
@Table(name = "group_member")
class GroupMember (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user : Users,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    val group: Group,
    var memberNickname: String? = null,
) : BaseEntity()