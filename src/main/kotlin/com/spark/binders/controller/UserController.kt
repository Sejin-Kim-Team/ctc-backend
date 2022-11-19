package com.spark.binders.controller

import com.spark.binders.auth.JwtTokenProvider
import com.spark.binders.dto.UserResponse
import com.spark.binders.service.UserService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.SessionAttribute
import javax.servlet.http.HttpSession

@RestController
class UserController(
    private val userService: UserService,
    private val tokenProvider: JwtTokenProvider,
) {
    @GetMapping("/login-success")
    fun getToken(
        session: HttpSession,
        @SessionAttribute(name = "token") token: String,
    ): UserResponse {

        val tokenResult = tokenProvider.validateToken(token)
        val user = userService.findByUserId(tokenResult.userId!!)
        val response = UserResponse(user, token = token)
        session.removeAttribute("token")
        return response
    }

    @QueryMapping
    fun getMe(@Argument userId: String) : UserResponse {
        val userId = SecurityContextHolder.getContext().authentication.name
        return UserResponse(userService.findByUserId(userId))
    }

    @MutationMapping
    fun deleteUser(@Argument userId : String) : Boolean {
        return userService.deleteUser(userId)
    }
}