package io.edsoncunha.cinemademospringboot.domain.services

import io.edsoncunha.cinemademospringboot.domain.entities.Movie
import io.edsoncunha.cinemademospringboot.domain.entities.MovieRating
import io.edsoncunha.cinemademospringboot.domain.exceptions.NotFoundException
import io.edsoncunha.cinemademospringboot.domain.repositories.MovieRatingRepository
import io.edsoncunha.cinemademospringboot.domain.repositories.MovieRepository
import org.springframework.stereotype.Service

@Service
class MovieService(
    private val movieRepository: MovieRepository,
    private val movieRatingRepository: MovieRatingRepository
) {
    fun getMovie(imdbId: String): Movie {
        val movie = movieRepository.findByImdbId(imdbId) ?: throw NotFoundException()
        return movie.also { it.customersRating = getRating(imdbId) }
    }

    fun getRating(imdbId: String) = movieRatingRepository.getAverageRateByImdbId(imdbId)

    fun rateMovie(imdbId: String, rating: Int) {
        val movie = movieRepository.findByImdbId(imdbId) ?: throw NotFoundException()

        movieRatingRepository.save(
            MovieRating(
                movie = movie,
                rating = rating
            )
        )
    }
}