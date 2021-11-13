package io.edsoncunha.cinemademospringboot.adapters

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("omdb")
class OmdbProperties {
    lateinit var url: String
    lateinit var apiKey: String
}
