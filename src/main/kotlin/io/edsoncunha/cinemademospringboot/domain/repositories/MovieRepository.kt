package io.edsoncunha.cinemademospringboot.domain.repositories

import io.edsoncunha.cinemademospringboot.domain.entities.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository : JpaRepository<Movie, Long> {
    @Query("select * from movie where imdb_id = ?1", nativeQuery = true)
    fun findByImdbId(imdbId: String) : Movie?
}