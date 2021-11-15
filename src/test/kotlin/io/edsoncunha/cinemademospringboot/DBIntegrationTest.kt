package io.edsoncunha.cinemademospringboot

import io.edsoncunha.cinemademospringboot.domain.exceptions.NotFoundException
import io.edsoncunha.cinemademospringboot.domain.services.MovieService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("it")
class DBIntegrationTest {

    companion object {
        @Container
        val container = PostgreSQLContainer<Nothing>("postgres:12.5-alpine").apply {
            withUsername("test-user")
            withPassword("test-pwd")
            withDatabaseName("cinemademo")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            container.start()
            registry.add("spring.datasource.url", container::getJdbcUrl);
            registry.add("spring.datasource.password=", container::getPassword);
            registry.add("spring.datasource.username=", container::getUsername);
        }
    }

    @Autowired
    private lateinit var service: MovieService



    @ParameterizedTest(name = "Check if \"{0}\" ({1}) is in initial database load")
    @CsvSource(
        "'The Fast and the Furious', 'tt0232500'",
        "'2 Fast 2 Furious', 'tt0322259'",
        "'The Fast and the Furious: Tokyo Drift', 'tt0463985'",
        "'Fast & Furious', 'tt1013752'",
        "'Fast Five', 'tt1596343'",
        "'Fast & Furious 6', 'tt1905041'",
        "'Furious 7', 'tt2820852'",
        "'The Fate of the Furious', 'tt4630562'"
    )
    fun `fetch movie from initial database load`(name: String, imdbId: String) {
        service.getMovie(imdbId)
    }

    @Test
    fun `Exception is thrown when movie is not found`() {
        assertThrows<NotFoundException> { service.getMovie("dummy") }
    }
}
