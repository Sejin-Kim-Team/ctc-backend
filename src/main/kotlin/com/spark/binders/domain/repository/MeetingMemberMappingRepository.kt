package com.spark.binders.domain.repository;

import com.spark.binders.domain.entity.MeetingMemberMapping
import org.springframework.data.jpa.repository.JpaRepository

interface MeetingMemberMappingRepository : JpaRepository<MeetingMemberMapping, Long> {
}