package io.edsoncunha.cinemademospringboot.domain.entities

import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalTime
import javax.persistence.*
import javax.validation.constraints.Min

@Entity(name = "movie_sessions")
class Session(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    val movie: Movie,

    @Column var dayOfWeek: DayOfWeek,

    @Column var sessionTime: LocalTime,

    @Column var price: BigDecimal,

    @Column var room: String, // this could be another entity, but it is supposed to be just a small demo, right?

    @Column @Min(1) var capacity: Int
)