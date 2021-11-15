package io.edsoncunha.cinemademospringboot.jobs

import io.edsoncunha.cinemademospringboot.adapters.OmdbAdapter
import io.edsoncunha.cinemademospringboot.domain.repositories.MovieRepository
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isLessThanOrEqualTo

@ExtendWith(MockKExtension::class)
internal class MovieUpdaterJobTest {

    @RelaxedMockK
    private lateinit var repository: MovieRepository

    @RelaxedMockK
    private lateinit var omdbAdapter: OmdbAdapter

    @BeforeEach
    fun setup() {

    }

    @ParameterizedTest(name = "Expect that {0} movies and quota of {1} calls generate delays of {2} milliseconds")
    @CsvSource(
        "5, 200, 2160000",
        "1, 1, 86400000",
        "200, 50, 345600000"
    )
    fun `tests delay calculations`(numberOfMovies: Long, apiQuota: Int, expectedDelay: Long) {
        every { repository.count() } returns numberOfMovies

        val job = MovieUpdaterJob(repository, apiQuota, omdbAdapter)

        expectThat(job.getDelayInMilliseconds()).isEqualTo(expectedDelay)
    }


    @ParameterizedTest(name = "Expect that quota of {1} is not exceeded for {0} movies")
    @CsvSource(
        "5, 200",
        "1, 1",
        "200, 50"
    )
    fun `ensure quota is not exceeded delay calculations`(numberOfMovies: Long, apiQuota: Int) {
        every { repository.count() } returns numberOfMovies

        val job = MovieUpdaterJob(repository, apiQuota, omdbAdapter)

        val delay = job.getDelayInMilliseconds()

        val numberOfExpectedCalls = (86_400_000L / delay) * numberOfMovies

        expectThat(numberOfExpectedCalls).isLessThanOrEqualTo(apiQuota.toLong())
    }

    @Test
    fun `if there are no movies, an arbitrary wait of 30 seconds should be returned`() {
        every { repository.count() } returns 0

        val job = MovieUpdaterJob(repository, 10, omdbAdapter)

        val delay = job.getDelayInMilliseconds()

        expectThat(delay).isEqualTo(30_000L)
    }
}
