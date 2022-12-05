package com.spark.binders.domain.repository;

import com.spark.binders.domain.entity.Group
import com.spark.binders.domain.entity.GroupMember
import com.spark.binders.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface GroupMemberRepository : JpaRepository<GroupMember, Long> {
    fun existsByUserAndGroup(user : User, group: Group) : Boolean
    fun findByUserAndGroup(user : User, group: Group) : GroupMember?
}