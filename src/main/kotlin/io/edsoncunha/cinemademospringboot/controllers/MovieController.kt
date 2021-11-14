package io.edsoncunha.cinemademospringboot.controllers

import io.edsoncunha.cinemademospringboot.domain.dto.UserMovieRating
import io.edsoncunha.cinemademospringboot.domain.entities.Movie
import io.edsoncunha.cinemademospringboot.domain.services.MovieService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Movies")
class MovieController(private val movieService: MovieService) {
    @GetMapping("/{id}")
    @Operation(summary = "Gets information from a specific movie, such as description and ratings")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "404", description = "Movie not found")
        ]
    )
    fun getMovie(@PathVariable("id") id: String): Movie {
        return movieService.getMovie(id)
    }

    @PostMapping("/{id}/rate")
    @Operation(summary = "Stores a new consumer rating for a given movie")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Created"),
            ApiResponse(responseCode = "404", description = "Movie not found")
        ]
    )
    fun rateMovie(@PathVariable("id") id: String, @RequestBody userRating: UserMovieRating): ResponseEntity<Void> {
        movieService.rateMovie(id, userRating.rating)
        return ResponseEntity<Void>(HttpStatus.CREATED)
    }
}