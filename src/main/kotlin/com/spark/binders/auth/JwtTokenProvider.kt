package com.spark.binders.auth

import com.spark.binders.domain.entity.User
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import mu.KotlinLogging
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${jwt.token-validity-in-seconds}") tokenValidityInSeconds: Long
) :InitializingBean {
    private val log= KotlinLogging.logger {  }
    private val tokenValidityInMilliseconds: Long
    private var key: Key? = null

    init {
        tokenValidityInMilliseconds = tokenValidityInSeconds * 1000
    }

    override fun afterPropertiesSet() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512)
    }

    fun createToken(user: User) : String {
        val validity = Date(Date().time + tokenValidityInMilliseconds)
        return Jwts.builder()
            .setSubject(user.userId)
            .claim("name", user.name)
            .claim("email", user.email)
            .claim("gender", user.gender.name)
            .signWith(key ,SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()
    }

    fun validateToken(token: String?) : TokenResult {
        try {
            val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)

            return TokenResult(true, claims.body.subject)
        } catch (e: SecurityException) {
            log.info("잘못된 JWT 서명입니다.")
        } catch (e: MalformedJwtException) {
            log.info("잘못된 JWT 서명입니다.")
        } catch (e: ExpiredJwtException) {
            log.info("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            log.info("지원되지 않는 JWT 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            log.info("JWT 토큰이 잘못되었습니다.")
        }
        return TokenResult(false)
    }

}

data class TokenResult(
    val isSuccess : Boolean,
    val userId: String?=null,
)