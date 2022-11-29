package com.spark.binders.config

import com.spark.binders.auth.CustomAuthenticationSuccessHandler
import com.spark.binders.auth.JwtTokenFilter
import com.spark.binders.auth.JwtTokenProvider
import com.spark.binders.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsUtils

@EnableWebSecurity
@Configuration
class WebSecurityConfig(
    private val userService: UserService,
    private val tokenProvider: JwtTokenProvider,
) {

    @Bean
    fun webCustomInitializer() : WebSecurityCustomizer {
        return WebSecurityCustomizer {
            web ->  web.ignoring().requestMatchers("/graphiql")
        }
    }

    @Bean
    fun filterChain(http: HttpSecurity) : SecurityFilterChain {

        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .cors().disable()
            .authorizeHttpRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .requestMatchers("/login", "/error", "/login/**", "/graphiql").permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2Login()
            .successHandler(CustomAuthenticationSuccessHandler(userService, tokenProvider))
            .userInfoEndpoint()
            .userService(userService)

        http.addFilterBefore(JwtTokenFilter(userService, tokenProvider), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}