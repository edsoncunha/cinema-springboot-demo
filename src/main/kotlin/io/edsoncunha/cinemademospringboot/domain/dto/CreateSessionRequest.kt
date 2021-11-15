package io.edsoncunha.cinemademospringboot.domain.dto

import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalTime
import javax.validation.constraints.Min

data class CreateSessionRequest(
    val dayOfWeek: DayOfWeek,
    val sessionTime: LocalTime,
    val price: BigDecimal,
    val room: String, // this could be another entity, but it is supposed to be just a small demo, right?
    @Min(1) val capacity: Int
)