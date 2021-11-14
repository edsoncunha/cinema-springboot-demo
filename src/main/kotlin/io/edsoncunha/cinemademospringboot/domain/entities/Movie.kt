package io.edsoncunha.cinemademospringboot.domain.entities

import java.time.LocalDate
import javax.persistence.*

@Entity
class Movie (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = true)
    val title: String,

    @Column(nullable = false)
    val plot: String,

    @Column(nullable = false)
    val releaseDate: LocalDate?,

    @Column(nullable = true)
    val customersRating: Double?,

    @Column(nullable = false)
    val numberOfCustomerRatings: Int,

    @Column(nullable = true)
    val imdbRating: Double?,

    @Column(nullable = true)
    val rottenTomatoesRating: Double?,

    @Column(nullable = true)
    val metacriticRating: Double?,

    @Column(nullable = true)
    val runtimeInMinutes: Int?
)