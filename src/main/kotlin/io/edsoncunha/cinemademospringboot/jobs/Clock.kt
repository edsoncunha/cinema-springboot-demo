package io.edsoncunha.cinemademospringboot.jobs

import org.springframework.stereotype.Service
import java.util.*

/**
 * Simple Clock class, just for making easier to workaround static methods and perform mocking
 * */
@Service
class Clock {
    fun now(): Date = Date()
}