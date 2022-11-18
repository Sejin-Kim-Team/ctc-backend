package com.spark.binders.service

import com.spark.binders.auth.OAuth2UserInfo
import com.spark.binders.auth.PrincipalDetails
import com.spark.binders.domain.entity.User
import com.spark.binders.domain.repository.UserRepository
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository
) : DefaultOAuth2UserService() {

    private val log = KotlinLogging.logger {  }

    override fun loadUser(userRequest : OAuth2UserRequest) : OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        val provider = userRequest.clientRegistration.registrationId

        val oauth2UserInfo = OAuth2UserInfo(
            attributes = oAuth2User.attributes,
        )
        val userId = provider + "_" + oauth2UserInfo.getProviderId()
        //TODO password encryption 필요
        val randomPassword = UUID.randomUUID().toString().substring(0, 20)

        var user = userRepository.findByIdOrNull(userId)
        if(user == null) {
            user = User(
                userId= userId,
                name= oauth2UserInfo.getNickname(),
                snsId = oauth2UserInfo.getEmail(),
                email = oauth2UserInfo.getEmail(),
                password = randomPassword,
                gender = oauth2UserInfo.getGender()
            )
            userRepository.save(user)
        }
        return PrincipalDetails(user, oauth2UserInfo)
    }
}