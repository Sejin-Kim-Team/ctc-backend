package com.spark.binders.controller

import com.spark.binders.auth.JwtTokenProvider
import com.spark.binders.dto.GroupMemberRequest
import com.spark.binders.dto.UserRequest
import com.spark.binders.dto.UserResponse
import com.spark.binders.service.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.SessionAttribute

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

    @QueryMapping("getMe")
    fun getMe() : UserResponse {
        return UserResponse(userService.getMe(getUserId()))
    }
    @MutationMapping("updateMe")
    fun updateMe(@Argument userRequest: UserRequest): UserResponse {
        return UserResponse(userService.updateMe(getUserId(), userRequest))
    }

    @MutationMapping("joinGroup")
    fun joinGroup(@Argument groupMemberRequest: GroupMemberRequest) : UserResponse {
        return UserResponse(userService.joinGroup(getUserId(), groupMemberRequest))
    }

    @MutationMapping("quitGroup")
    fun quitGroup(@Argument groupId: Long): UserResponse {
        return UserResponse(userService.quitGroup(getUserId(), groupId))
    }

    @MutationMapping("withdraw")
    fun withdraw() : Boolean {
        return userService.deleteUser(getUserId())
    }

    @MutationMapping(name = "deleteUser")
    fun deleteUser(@Argument userId : String) : Boolean {
        return userService.deleteUser(userId)
    }

    private fun getUserId() : String {
        return SecurityContextHolder.getContext().authentication.name
    }
}
