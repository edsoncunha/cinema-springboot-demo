package io.edsoncunha.cinemademospringboot.domain.repositories

import io.edsoncunha.cinemademospringboot.domain.entities.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    @Query("SELECT * FROM cinema_admins WHERE username = ?1", nativeQuery = true)
    fun findByUserName(email: String): User?

    @Query("SELECT * FROM cinema_admins WHERE email = ?1", nativeQuery = true)
    fun findByEmail(email: String): User?
}