package io.edsoncunha.cinemademospringboot.domain.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

class AuthRequest(
    val username: @NotNull @Email String,
    val password: @NotNull String
)