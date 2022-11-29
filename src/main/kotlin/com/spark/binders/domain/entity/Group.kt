package com.spark.binders.domain.entity

import jakarta.persistence.*


@Entity
@Table(name = "groups")
class Group (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var groupName: String,
    var location: String? = null,
    var locationDetail: String? = null,
    var description: String? = null,
    var groupImage: String? = null,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val groupMembers: MutableList<GroupMember>? = mutableListOf(),
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val meetings: MutableList<Meetings>? = mutableListOf(),
) : BaseEntity()
