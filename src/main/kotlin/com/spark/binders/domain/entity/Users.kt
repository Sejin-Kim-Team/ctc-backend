package com.spark.binders.domain.entity

import com.spark.binders.domain.entity.enum.Gender
import javax.persistence.*

@Entity
@Table(name = "users")
class Users(
    @Id
    val userId : String?,
    val snsId : String,
    var password: String,
    val name: String,
    var email: String?,
    @Enumerated(EnumType.STRING)
    val gender: Gender,
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    val groupMembers: MutableList<GroupMember>?= mutableListOf()

) : BaseEntity()
