package io.edsoncunha.cinemademospringboot.domain.services

import io.edsoncunha.cinemademospringboot.domain.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService (private val userRepository: UserRepository) {
    fun getByEmail(email: String)  = userRepository.findByEmail(email)
    fun getByUsername(email: String)  = userRepository.findByUserName(email)
}