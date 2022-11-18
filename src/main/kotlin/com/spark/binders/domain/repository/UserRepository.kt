package com.spark.binders.domain.repository

import com.spark.binders.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String>