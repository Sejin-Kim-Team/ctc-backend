package com.spark.binders.auth

import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.filter.GenericFilterBean
import javax.naming.AuthenticationException
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


class JwtTokenFilter(
    private val userDetailsService: UserDetailsService,
    private val jwtTokenProvider : JwtTokenProvider,
) : GenericFilterBean(){

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpServletRequest = request as HttpServletRequest

        val requestUri = httpServletRequest.requestURI

        val jwt =
            if(requestUri.equals("/login-success")) {
                httpServletRequest.session.getAttribute("token").toString()
            }
            else resolveToken(httpServletRequest)

        val validateResult = jwtTokenProvider.validateToken(jwt)
        if(validateResult.isSuccess) {
            val userDetails = userDetailsService.loadUserByUsername(validateResult.userId)
            val token = UsernamePasswordAuthenticationToken(
                userDetails.username,
                null,
                userDetails.authorities
            )
            SecurityContextHolder.getContext().authentication = token
            chain?.doFilter(request, response)
        } else {
            throw AuthenticationException("Invalid Token")
        }
    }

    private fun resolveToken(request: HttpServletRequest) : String? {
        val bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION)

        return if(!bearerToken.isNullOrEmpty() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        }  else null
    }
}