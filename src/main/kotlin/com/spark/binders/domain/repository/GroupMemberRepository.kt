package com.spark.binders.domain.repository;

import com.spark.binders.domain.entity.GroupMember
import org.springframework.data.jpa.repository.JpaRepository

interface GroupMemberRepository : JpaRepository<GroupMember, Long> {
}