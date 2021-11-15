package io.edsoncunha.cinemademospringboot.domain.services

import io.edsoncunha.cinemademospringboot.domain.dto.CreateSessionRequest
import io.edsoncunha.cinemademospringboot.domain.dto.MovieWithSessionsView
import io.edsoncunha.cinemademospringboot.domain.dto.SessionView
import io.edsoncunha.cinemademospringboot.domain.dto.UpdateSessionRequest
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
    fun getMovie(id: Long): Movie {
        val movie = movieRepository.findById(id).orElseThrow { NotFoundException("movie") }
        return movie.also { it.customersRating = getRating(id) }
    }

    fun getRating(id: Long) = movieRatingRepository.getAverageRateById(id)

    fun rateMovie(id: Long, rating: Int) {
        val movie = movieRepository.findById(id).orElseThrow { NotFoundException("movie") }

        movieRatingRepository.save(
            MovieRating(
                movie = movie,
                rating = rating
            )
        )
    }

    fun createSession(id: Long, request: CreateSessionRequest): Session {
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

    fun updateSession(id: Long, request: UpdateSessionRequest) {
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

    fun getMovieSessions(id: Long): MovieWithSessionsView {
        val movie = getMovie(id)

        val sessions = movieSessionRepository.findAllByMovieId(id)

        return MovieWithSessionsView(
            title = movie.title,
            plot = movie.plot,
            rating = movieRatingRepository.getAverageRateById(id),
            sessions = sessions.map {
                SessionView(
                    dayOfWeek = it.dayOfWeek,
                    capacity = it.capacity,
                    price = it.price,
                    room = it.room,
                    sessionTime = it.sessionTime
                )
            }
        )
    }
}