package io.edsoncunha.cinemademospringboot.configuration.security


import io.edsoncunha.cinemademospringboot.domain.repositories.UserRepository
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtTokenFilter(private val jwtTokenUtil: JwtTokenUtil, private val userRepo: UserRepository) :
    OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        // Get authorization header and validate
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (header.isNullOrBlank() || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        // Get jwt token and validate
        val token = header.split(" ".toRegex()).toTypedArray()[1].trim { it <= ' ' }
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response)
            return
        }

        // Get user identity and set it on the spring security context
        val user = userRepo.findByUserName(jwtTokenUtil.getUsername(token))!!
        val userDetails: UserDetails =
            org.springframework.security.core.userdetails.User(user.username, user.password, arrayListOf())

        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, null,
            Optional.ofNullable(userDetails).map { obj: UserDetails -> obj.authorities }.orElse(listOf())
        )
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }
}
