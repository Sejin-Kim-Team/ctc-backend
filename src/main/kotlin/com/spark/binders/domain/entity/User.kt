package com.spark.binders.domain.entity

import com.spark.binders.domain.entity.enum.Gender
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    val userId : String?,
    val snsId : String,
    var password: String,
    var name: String?=null,
    var email: String?=null,
    @Enumerated(EnumType.STRING)
    val gender: Gender,
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    val groupMembers: MutableList<GroupMember>?= mutableListOf()

) : BaseEntity()
