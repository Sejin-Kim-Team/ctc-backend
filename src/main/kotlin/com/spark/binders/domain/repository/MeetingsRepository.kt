package com.spark.binders.domain.repository;

import com.spark.binders.domain.entity.Group
import com.spark.binders.domain.entity.Meetings
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface MeetingsRepository : JpaRepository<Meetings, Long> {

    fun findByGroup(group: Group, pageable: Pageable) : List<Meetings>

    @EntityGraph(attributePaths = ["joinedMembers"], type = EntityGraph.EntityGraphType.LOAD)
    fun findWithJoinedMembersById(id: Long) : Meetings?
}