package io.edsoncunha.cinemademospringboot.adapters

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import kotlin.properties.Delegates

@Component
@ConfigurationProperties("omdb")
class OmdbProperties {
    lateinit var url: String
    var connectTimeout: Long = 0
    var readTimeout: Long = 0
    lateinit var apiKey: String
}
