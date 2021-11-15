package io.edsoncunha.cinemademospringboot.domain.entities

import java.io.Serializable
import javax.persistence.*

@Entity(name = "cinema_admins")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = true)
    val username: String,

    @Column(nullable = false)
    val password: String
) : Serializable