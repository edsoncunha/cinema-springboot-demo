package io.edsoncunha.cinemademospringboot.domain.repositories

import io.edsoncunha.cinemademospringboot.domain.entities.MovieRating
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRatingRepository : CrudRepository<MovieRating, Long> {
    @Query("SELECT AVG(rating) FROM movie_rating " +
            "WHERE movie_id = ?1", nativeQuery = true)
    fun getAverageRateById(id: Long): Double?
}