package io.edsoncunha.cinemademospringboot.domain.entities

import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Entity
class MovieRating(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // oh gawd, mapping an entire entity just because of a foreign key column...
    @ManyToOne(fetch = FetchType.LAZY)
    val movie: Movie,

    @Column
    @Min(1)
    @Max(5)
    val rating: Int
)