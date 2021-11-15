package io.edsoncunha.cinemademospringboot.api

import io.edsoncunha.cinemademospringboot.configuration.security.JwtTokenUtil
import io.edsoncunha.cinemademospringboot.domain.dto.AuthRequest
import io.edsoncunha.cinemademospringboot.domain.dto.UserView
import io.edsoncunha.cinemademospringboot.domain.services.UserService

import io.swagger.v3.oas.annotations.tags.Tag

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@Tag(name = "Authentication")
@RestController
@RequestMapping(path = ["api/public"])
class AuthApi(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil,
    private val userService: UserService
) {
    @PostMapping("login")
    fun login(@RequestBody request: @Valid AuthRequest): ResponseEntity<UserView> {
        return try {
            val authenticate = authenticationManager
                .authenticate(UsernamePasswordAuthenticationToken(request.username, request.password))

            val user = authenticate.principal as org.springframework.security.core.userdetails.User

            ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(user))
                .body(user.toUserView())
        } catch (ex: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

}

private fun org.springframework.security.core.userdetails.User.toUserView(): UserView {
    return UserView(
        id = this.username,
        username = this.username
    )
}
