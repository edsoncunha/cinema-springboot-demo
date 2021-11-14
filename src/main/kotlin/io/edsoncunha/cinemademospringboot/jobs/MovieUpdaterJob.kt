package io.edsoncunha.cinemademospringboot.jobs

import io.edsoncunha.cinemademospringboot.adapters.OmdbAdapter
import io.edsoncunha.cinemademospringboot.adapters.OmdbMovie
import io.edsoncunha.cinemademospringboot.domain.entities.Movie
import io.edsoncunha.cinemademospringboot.domain.repositories.MovieRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MovieUpdaterJob(
    private val repository: MovieRepository,
    @Value("\${omdb.daily-quota}") private val omdbDailyQuota: Int,
    private val omdbAdapter: OmdbAdapter
) {
    private val log = LoggerFactory.getLogger(MovieUpdaterJob::class.java)
    private val MINIMUM_DELAY_WHEN_NO_MOVIES_FOUND = 30_000L

    fun updateAllMovies() {
        try {
            repository.findAll().forEach {
                log.info("Started updating movie ${it.title}")

                val omdbData = omdbAdapter.fetchMovie(it.imdbId)
                it.updateWith(omdbData)
            }
        } catch (error: Error) {
            log.error("Update round failed", error)
        }
    }


    /**
     * Calculates the delay between executions, based on the number of movies and OMDB API limit,
     * so that the daily quota is spent in regular intervals during 24 hours.
     */
    fun getDelayInMilliseconds(): Long {
        val totalMillisecondsInADay = 86_400_000 // 1000 * 60 * 60 * 24

        val numberOfMovies: Long

        try {
            numberOfMovies = repository.count()
        } catch (error: Error) {
            log.error("Could not fetch number of movies", error)
            return 5_000 // arbitrary delay of 5 minutes to avoid flooding the database
        }

        val delay = (totalMillisecondsInADay / omdbDailyQuota) * numberOfMovies

        return if (delay > 0) delay else MINIMUM_DELAY_WHEN_NO_MOVIES_FOUND
    }

    private fun Movie.updateWith(omdbMovie: OmdbMovie) {
        try {
            val movieToUpdate = Movie(
                id = this.id,
                imdbId = this.imdbId,
                title = omdbMovie.Title,
                plot = omdbMovie.Plot,
                runtimeInMinutes = omdbMovie.Runtime.parseInteger()
            )

            repository.save(movieToUpdate)

            log.info("Movie ${movieToUpdate.title} updated successfully")
        } catch (error: Error) {
            log.error("Could not update movie ${this.title}", error)
        }
    }

    private fun String?.parseInteger(): Int? {
        return this?.replace("[^0-9]".toRegex(), "")?.toInt()
    }
}