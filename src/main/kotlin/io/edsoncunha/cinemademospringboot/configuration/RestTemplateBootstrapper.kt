package io.edsoncunha.cinemademospringboot.configuration

import io.edsoncunha.cinemademospringboot.adapters.OmdbProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class RestTemplateBootstrapper {
    @Bean
    fun restTemplate(builder: RestTemplateBuilder, config: OmdbProperties) = builder
        .setConnectTimeout(Duration.ofMillis(config.connectTimeout))
        .setReadTimeout(Duration.ofMillis(config.readTimeout))
        .build()
}