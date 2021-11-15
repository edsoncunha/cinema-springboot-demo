package io.edsoncunha.cinemademospringboot.domain.services

import io.edsoncunha.cinemademospringboot.domain.dto.CreateMovieSessionRequest
import io.edsoncunha.cinemademospringboot.domain.dto.UpdateMovieSessionRequest
import io.edsoncunha.cinemademospringboot.domain.entities.Movie
import io.edsoncunha.cinemademospringboot.domain.entities.MovieRating
import io.edsoncunha.cinemademospringboot.domain.entities.Session
import io.edsoncunha.cinemademospringboot.domain.exceptions.NotFoundException
import io.edsoncunha.cinemademospringboot.domain.repositories.MovieRatingRepository
import io.edsoncunha.cinemademospringboot.domain.repositories.MovieRepository
import io.edsoncunha.cinemademospringboot.domain.repositories.MovieSessionRepository
import org.springframework.stereotype.Service

@Service
class MovieService(
    private val movieRepository: MovieRepository,
    private val movieRatingRepository: MovieRatingRepository,
    private val movieSessionRepository: MovieSessionRepository
) {
    fun getMovie(imdbId: String): Movie {
        val movie = movieRepository.findByImdbId(imdbId) ?: throw NotFoundException("movie")
        return movie.also { it.customersRating = getRating(imdbId) }
    }

    fun getRating(imdbId: String) = movieRatingRepository.getAverageRateByImdbId(imdbId)

    fun rateMovie(imdbId: String, rating: Int) {
        val movie = movieRepository.findByImdbId(imdbId) ?: throw NotFoundException("movie")

        movieRatingRepository.save(
            MovieRating(
                movie = movie,
                rating = rating
            )
        )
    }

    fun createSession(id: Long, request: CreateMovieSessionRequest): Session {
        val movie = movieRepository.findById(id).orElseThrow { NotFoundException("movie") }

        return movieSessionRepository.save(
            Session(
                movie = movie,
                dayOfWeek = request.dayOfWeek,
                sessionTime = request.sessionTime,
                price = request.price,
                room = request.room,
                capacity = request.capacity
            )
        )
    }

    fun updateSession(id: Long, request: UpdateMovieSessionRequest) {
        val sessionToUpdate = movieSessionRepository.findById(id).orElseThrow { NotFoundException("session") }

        sessionToUpdate.apply {
            dayOfWeek = request.dayOfWeek
            sessionTime = request.sessionTime
            price = request.price
            capacity = request.capacity
            room = request.room
        }

        movieSessionRepository.save(sessionToUpdate)
    }

    fun deleteSession(id: Long) = movieSessionRepository.deleteById(id)
}