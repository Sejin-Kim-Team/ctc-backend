package com.spark.binders.auth

import com.spark.binders.domain.entity.enum.Gender

class OAuth2UserInfo(
    val attributes: MutableMap<String, Any>,
) {
    fun getProviderId() : String {
        return attributes["id"].toString()
    }

    fun getEmail() : String {
        val attributeAccounts = attributes["kakao_account"] as Map<*, *>
        return attributeAccounts["email"].toString()
    }
    fun getGender() : Gender {
        val attributeAccounts = attributes["kakao_account"] as Map<*, *>
        return Gender(attributeAccounts["gender"].toString())
    }
    fun getNickname() : String {
        val attributeAccounts = attributes["kakao_account"] as Map<*, *>
        val profiles = attributeAccounts["profile"] as Map<*, *>
        return profiles["nickname"].toString()
    }
}