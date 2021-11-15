package io.edsoncunha.cinemademospringboot.api


import com.fasterxml.jackson.databind.ObjectMapper
import io.edsoncunha.cinemademospringboot.domain.dto.AuthRequest
import io.edsoncunha.cinemademospringboot.domain.dto.UserView
import io.edsoncunha.cinemademospringboot.fromJson
import io.edsoncunha.cinemademospringboot.toJson
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import strikt.api.expectThat
import strikt.assertions.isEqualTo


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("it")
class AuthIntegrationTest {

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
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val password = "P@ssw0rd"

    @Test
    fun `login success`() {
        val userView = UserView("1", "admin")

        val request = AuthRequest(
            userView.username,
            password
        )

        val createResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/public/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(objectMapper, request))
            )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.AUTHORIZATION))
            .andReturn()

        val authUserView = fromJson(objectMapper, createResult.response.contentAsString, UserView::class.java)

        expectThat(userView.username).isEqualTo(authUserView.username)
    }

    @Test
    fun `login fail`() {
        val userView = UserView("1", "bogus")

        val request = AuthRequest(
            userView.username,
            "asdfsdfa"
        )

        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/public/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(objectMapper, request))
            )
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            .andExpect(MockMvcResultMatchers.header().doesNotExist(HttpHeaders.AUTHORIZATION))
            .andReturn()
    }
}