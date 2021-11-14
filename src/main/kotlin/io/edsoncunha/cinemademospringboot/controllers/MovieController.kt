package io.edsoncunha.cinemademospringboot.controllers

import io.edsoncunha.cinemademospringboot.domain.entities.Movie
import io.edsoncunha.cinemademospringboot.domain.repositories.MovieRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Movies")
class MovieController(private val movieRepository: MovieRepository) {
    @GetMapping("/{id}")
    @Operation(summary = "Gets information from a specific movie, such as description and ratings")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "Movie not found")
        ]
    )
    fun getMovie(id: String): Movie {
        return movieRepository.findByImdbId(id)
    }
}