package io.edsoncunha.cinemademospringboot.domain.repositories

import io.edsoncunha.cinemademospringboot.domain.entities.Movie
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository : CrudRepository<Movie, Long> {
    @Query("SELECT * FROM movie WHERE imdb_id = ?1", nativeQuery = true)
    fun findByImdbId(imdbId: String): Movie?
}
