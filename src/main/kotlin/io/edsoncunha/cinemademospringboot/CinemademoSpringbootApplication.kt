package io.edsoncunha.cinemademospringboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@SpringBootApplication
class CinemademoSpringbootApplication

@OpenAPIDefinition(
	info = Info(
		title = "Cinema Demo",
		version = "0.1"
	)
)
object Api

fun main(args: Array<String>) {
	runApplication<CinemademoSpringbootApplication>(*args)
}
