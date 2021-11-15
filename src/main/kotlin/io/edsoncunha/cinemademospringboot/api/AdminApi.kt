package io.edsoncunha.cinemademospringboot.api

import io.edsoncunha.cinemademospringboot.domain.dto.CreateMovieSessionRequest
import io.edsoncunha.cinemademospringboot.domain.dto.UpdateMovieSessionRequest
import io.edsoncunha.cinemademospringboot.domain.services.MovieService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Administration")
class AdminApi(private val movieService: MovieService) {
    @PostMapping("/movie/{id}/session")
    @Operation(summary = "Adds a new exhibition session for a given movie")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Created"),
            ApiResponse(responseCode = "401", description = "User is not authenticated"),
            ApiResponse(responseCode = "403", description = "User is not authorized"),
            ApiResponse(responseCode = "404", description = "Movie not found")
        ]
    )
    fun createMovieSession(
        @PathVariable("id") id: Long,
        @RequestBody createSessionRequest: CreateMovieSessionRequest
    ): ResponseEntity<Void> {
        movieService.createSession(id, createSessionRequest)
        return ResponseEntity<Void>(HttpStatus.CREATED)
    }

    @PutMapping("/movie/{id}/session/{id}")
    @Operation(summary = "Updates an exhibition session for a given movie")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "401", description = "User is not authenticated"),
            ApiResponse(responseCode = "403", description = "User is not authorized"),
            ApiResponse(responseCode = "404", description = "Movie not found")
        ]
    )
    fun updateMovieSession(
        @PathVariable("id") id: Long,
        @RequestBody createSessionRequest: UpdateMovieSessionRequest
    ): ResponseEntity<Void> {
        movieService.updateSession(id, createSessionRequest)
        return ResponseEntity<Void>(HttpStatus.OK)
    }

    @DeleteMapping("/movie/{id}/session/{id}")
    @Operation(summary = "Updates an exhibition session for a given movie")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "401", description = "User is not authenticated"),
            ApiResponse(responseCode = "403", description = "User is not authorized"),
            ApiResponse(responseCode = "404", description = "Movie not found")
        ]
    )
    fun deleteMovieSession(
        @PathVariable("id") id: Long
    ): ResponseEntity<Void> {
        movieService.deleteSession(id)
        return ResponseEntity<Void>(HttpStatus.OK)
    }
}