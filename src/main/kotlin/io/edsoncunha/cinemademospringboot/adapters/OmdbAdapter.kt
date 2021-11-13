package io.edsoncunha.cinemademospringboot.adapters

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class OmdbAdapter(private val restTemplate: RestTemplate, private val config: OmdbProperties) {
    fun fetchMovie(id: String) : OmdbMovie? {
       return restTemplate.getForObject(config.url, OmdbMovie::class.java, mapOf("movieId" to id))
    }
}