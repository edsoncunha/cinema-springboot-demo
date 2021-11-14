package io.edsoncunha.cinemademospringboot.domain.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Movie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = true)
    val title: String,

    @Column(nullable = false)
    val plot: String,

    @Column(nullable = true)
    val runtimeInMinutes: Int?,

    @Column
    val imdbId: String,

    @Column(nullable = false)
    var lastUpdate: LocalDateTime = LocalDateTime.now()
)