package io.edsoncunha.cinemademospringboot.configuration

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RestTemplateBootstrapper {
    @Bean
    fun restTemplate(builder: RestTemplateBuilder) = builder.build()
}