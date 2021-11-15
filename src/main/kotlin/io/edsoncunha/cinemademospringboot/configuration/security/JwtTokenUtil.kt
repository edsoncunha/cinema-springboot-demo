package io.edsoncunha.cinemademospringboot.configuration.security


import io.edsoncunha.cinemademospringboot.domain.entities.User
import io.edsoncunha.cinemademospringboot.jobs.MovieUpdaterJob

import io.jsonwebtoken.*

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtTokenUtil {
    private val jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP"

    private val jwtIssuer = "example.io"

    private val logger = LoggerFactory.getLogger(MovieUpdaterJob::class.java)

    fun generateAccessToken(user: org.springframework.security.core.userdetails.User): String {
        return Jwts.builder()
            .setSubject(String.format("%s,%s", user.username, user.username))
            .setIssuer(jwtIssuer)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getUserId(token: String?): String {
        val claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
        return claims.subject.split(",".toRegex()).toTypedArray()[0]
    }

    fun getUsername(token: String?): String {
        val claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
        return claims.subject.split(",".toRegex()).toTypedArray()[1]
    }

    fun getExpirationDate(token: String?): Date {
        val claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
        return claims.expiration
    }

    fun validate(token: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            return true
        } catch (ex: SignatureException) {
            logger.error("Invalid JWT signature - {}", ex.message)
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token - {}", ex.message)
        } catch (ex: ExpiredJwtException) {
            logger.error("Expired JWT token - {}", ex.message)
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT token - {}", ex.message)
        } catch (ex: IllegalArgumentException) {
            logger.error("JWT claims string is empty - {}", ex.message)
        }
        return false
    }
}
