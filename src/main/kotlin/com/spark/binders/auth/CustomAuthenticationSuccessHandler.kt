package com.spark.binders.auth

import com.spark.binders.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler

class CustomAuthenticationSuccessHandler(
    private val userService: UserService,
    private val tokenProvider: JwtTokenProvider,
) : SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val username = authentication!!.name
        val user = userService.findByUserId(username)

        request?.session?.setAttribute("token", tokenProvider.createToken(user))

        redirectStrategy.sendRedirect(request, response, "/login-success")
    }
}