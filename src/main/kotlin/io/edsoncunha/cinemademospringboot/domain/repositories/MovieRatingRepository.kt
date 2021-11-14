package io.edsoncunha.cinemademospringboot.domain.repositories

import io.edsoncunha.cinemademospringboot.domain.entities.MovieRating
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRatingRepository : CrudRepository<MovieRating, Long> {
    @Query("SELECT AVG(mr.rating) FROM movie_rating mr " +
            "JOIN movie m " +
            "ON m.id = mr.movie_id " +
            "WHERE m.imdb_id = ?1", nativeQuery = true)
    fun getAverageRateByImdbId(imdbId: String): Double?
}