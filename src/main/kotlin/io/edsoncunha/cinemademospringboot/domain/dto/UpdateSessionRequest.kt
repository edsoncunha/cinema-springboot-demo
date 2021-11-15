package io.edsoncunha.cinemademospringboot.domain.dto

import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalTime
import javax.validation.constraints.Min

data class UpdateSessionRequest(
    val dayOfWeek: DayOfWeek,
    val sessionTime: LocalTime,
    val price: BigDecimal,
    val room: String,
    @Min(1) val capacity: Int
)