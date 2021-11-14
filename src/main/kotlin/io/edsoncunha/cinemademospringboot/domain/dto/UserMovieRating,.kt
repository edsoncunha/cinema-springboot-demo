package io.edsoncunha.cinemademospringboot.domain.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class UserMovieRating(@Min(value = 1) @Max(value = 5) val rating: Int)
