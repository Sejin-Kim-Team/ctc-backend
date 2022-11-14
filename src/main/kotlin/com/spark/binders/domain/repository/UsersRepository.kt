package com.spark.binders.domain.repository

import com.spark.binders.domain.entity.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UsersRepository : JpaRepository<Users, String>