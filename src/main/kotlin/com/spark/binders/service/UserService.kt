package com.spark.binders.service

import com.spark.binders.auth.CustomUserDetails
import com.spark.binders.auth.OAuth2UserInfo
import com.spark.binders.auth.CustomOAuth2User
import com.spark.binders.domain.entity.GroupMember
import com.spark.binders.domain.entity.User
import com.spark.binders.domain.repository.GroupMemberRepository
import com.spark.binders.domain.repository.GroupRepository
import com.spark.binders.domain.repository.UserRepository
import com.spark.binders.dto.GroupMemberRequest
import com.spark.binders.dto.KakaoUserResponse
import com.spark.binders.dto.UserRequest
import com.spark.binders.dto.UserResponse
import com.spark.binders.exception.NotFoundException
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Lazy
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.BodyInserters.fromFormData
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.UUID

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val groupMemberRepository: GroupMemberRepository,
    @Value("\${api.kakao.admin-key}")
    private val kakaoAdminKey : String,
    @Value("\${api.kakao.withdrawal-uri}")
    private val kakaoUnlink: String,
    @Lazy
    private val passwordEncoder: PasswordEncoder,
) : DefaultOAuth2UserService(), UserDetailsService {

    private val log = KotlinLogging.logger {  }

    override fun loadUser(userRequest : OAuth2UserRequest) : OAuth2User {
        val oAuth2User = super.loadUser(userRequest)

        val oauth2UserInfo = OAuth2UserInfo(
            attributes = oAuth2User.attributes,
        )
        val userId = oauth2UserInfo.getProviderId()
        val randomPassword = UUID.randomUUID().toString().substring(0, 20)
        val encodedPassword = passwordEncoder.encode(randomPassword)

        var user = userRepository.findByIdOrNull(userId)
        if(user == null) {
            user = User(
                userId= userId,
                name= oauth2UserInfo.getNickname(),
                snsId = oauth2UserInfo.getEmail(),
                email = oauth2UserInfo.getEmail(),
                password = encodedPassword,
                gender = oauth2UserInfo.getGender()
            )
            userRepository.save(user)
        }
        return CustomOAuth2User(oauth2UserInfo)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByIdOrNull(username!!)
        checkNotNull(user)
        return CustomUserDetails(user)
    }

    fun findByUserId(userId: String) : User {
        val user = userRepository.findByIdOrNull(userId)
        checkNotNull(user)
        return user
    }

    fun getMe(userId: String) : UserResponse {
        val user = userRepository.findWithGroupMembersByUserId(userId)
        checkNotNull(user)
        return UserResponse(user)
    }

    fun updateMe(userId: String, userRequest : UserRequest) : UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundException()

        return with(user) {
            name = userRequest.name?: name
            email = userRequest.email?: email

            UserResponse(userRepository.save(this))
        }
    }


    fun joinGroup(userId: String, groupMemberRequest : GroupMemberRequest): UserResponse {
        val group = groupRepository.findByIdOrNull(groupMemberRequest.groupId) ?: throw NotFoundException()
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundException()

        val groupMember = GroupMember(
            user=user,
            group = group,
            memberNickname = groupMemberRequest.nickname?: user.name
        )

        if(!groupMemberRepository.existsByUserAndGroup(user, group)) {
            val savedGroupMember = groupMemberRepository.save(groupMember)
            group.addMember(savedGroupMember)
            user.joinGroup(savedGroupMember)
        }

        return UserResponse(user)
    }

    fun quitGroup(userId:String, groupId: Long) :UserResponse {
        val group = groupRepository.findByIdOrNull(groupId) ?: throw NotFoundException()
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundException()
        val groupMember = groupMemberRepository.findByUserAndGroup(user, group) ?: throw NotFoundException()
        user.quitGroup(groupMember)
        group.removeMember(groupMember)

        groupMemberRepository.delete(groupMember)

        return UserResponse(user)
    }

    fun deleteUser(userId: String) : Boolean {
        val response = unlinkUserKakao(userId)
        if(response.id == userId.toLong()) {
            userRepository.deleteById(userId)
            return true
        }
        return false
    }


    fun unlinkUserKakao(userId: String) : KakaoUserResponse {

        return runBlocking {
            WebClient.create()
                .post()
                .uri(kakaoUnlink)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.AUTHORIZATION, "KakaoAK $kakaoAdminKey")
                .body(
                    fromFormData("target_id_type", "user_id")
                        .with("target_id", userId)
                )
                .retrieve()
                .awaitBody()
        }
    }
}
