package io.edsoncunha.cinemademospringboot.domain.exceptions

class NotFoundException(val entityName: String) : RuntimeException()