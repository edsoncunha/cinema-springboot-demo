package io.edsoncunha.cinemademospringboot.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

class NotFoundException : RuntimeException()