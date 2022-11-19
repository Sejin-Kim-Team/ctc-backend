package com.spark.binders.domain.repository;

import com.spark.binders.domain.entity.Group
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository : JpaRepository<Group, Long> {
}