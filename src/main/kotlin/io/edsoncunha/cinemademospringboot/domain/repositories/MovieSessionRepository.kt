package io.edsoncunha.cinemademospringboot.domain.repositories

import io.edsoncunha.cinemademospringboot.domain.entities.Session
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieSessionRepository : CrudRepository<Session, Long> {
    @Query("SELECT * FROM movie_sessions WHERE movie_id = ?1 ORDER BY day_of_week, session_time", nativeQuery = true)
    fun findAllByMovieId(id: Long): List<Session>
}
