package com.spark.binders.domain.repository;

import com.spark.binders.domain.entity.Meetings
import org.springframework.data.jpa.repository.JpaRepository

interface MeetingsRepository : JpaRepository<Meetings, Long> {
}