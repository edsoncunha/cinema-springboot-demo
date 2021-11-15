package io.edsoncunha.cinemademospringboot.configuration.security


import io.edsoncunha.cinemademospringboot.domain.entities.User
import io.edsoncunha.cinemademospringboot.domain.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class SecurityConfig(
    private val userRepo: UserRepository,
    private val jwtTokenFilter: JwtTokenFilter
) : WebSecurityConfigurerAdapter() {

    @Value("\${springdoc.api-docs.path}")
    private val restApiDocPath: String? = null

    @Value("\${springdoc.swagger-ui.path}")
    private val swaggerPath: String? = null

    private val logger = LoggerFactory.getLogger(SecurityConfig::class.java)

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(UserDetailsService { username: String? ->
            userRepo.findByUserName(username!!).asSpringUser()
                ?: throw UsernameNotFoundException(String.format("User: %s, not found", username))
        })
    }

    // Set password encoding schema
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(httpSecurity: HttpSecurity) {
        // Enable CORS and disable CSRF
        var http = httpSecurity.cors()
            .and()
            .csrf().disable()

        // Set session management to stateless
        http = http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

        // Set unauthorized requests exception handler
        http = http
            .exceptionHandling()
            .authenticationEntryPoint { _: HttpServletRequest?, response: HttpServletResponse, ex: AuthenticationException ->
                logger.error("Unauthorized request - {}", ex.message)
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.message)
            }
            .and()

        // Set permissions on endpoints
        http.authorizeRequests()
            // Swagger endpoints must be publicly accessible
            .antMatchers("/").permitAll()
            .antMatchers(String.format("%s/**", restApiDocPath)).permitAll()
            .antMatchers(String.format("%s/**", swaggerPath)).permitAll()
            // Our public endpoints
            .antMatchers("/api/public/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/movie/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/movie/**/rate").permitAll()
            .antMatchers(HttpMethod.GET, "/api/movie/**/sessions").permitAll()
            // Our private endpoints
            .anyRequest().authenticated()

        // Add JWT token filter
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    // Used by spring security if CORS is enabled.
    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    // Expose authentication manager bean
    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    init {
        // Inherit security context in async function calls
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL)
    }
}

private fun User?.asSpringUser(): UserDetails? {
    if (this == null) return null
    return org.springframework.security.core.userdetails.User(this.username, this.password, arrayListOf())
}
