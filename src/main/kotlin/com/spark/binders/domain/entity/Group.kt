package com.spark.binders.domain.entity

import javax.persistence.*

@Entity
@Table(name = "group")
class Group (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var groupName: String,
    var location: String? = null,
    var locationDetail: String? = null,
    var description: String? = null,
    var groupImage: String? = null,
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    val groupMember: MutableList<GroupMember>? = mutableListOf(),
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    val meetings: MutableList<Meetings>? = mutableListOf(),
) : BaseEntity()