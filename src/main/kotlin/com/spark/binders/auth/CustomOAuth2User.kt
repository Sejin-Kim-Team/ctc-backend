package com.spark.binders.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomOAuth2User(
    private val oAuth2UserInfo: OAuth2UserInfo,
    ) : OAuth2User {
    override fun getName(): String {
        return oAuth2UserInfo.getProviderId()
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return oAuth2UserInfo.attributes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
    }
}