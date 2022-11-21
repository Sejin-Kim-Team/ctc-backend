package com.spark.binders.auth

import com.spark.binders.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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