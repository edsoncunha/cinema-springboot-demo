package io.edsoncunha.cinemademospringboot

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

fun <T> toJson(objectMapper: ObjectMapper, `object`: T): String {
    return objectMapper.writeValueAsString(`object`)
}

fun <T> fromJson(objectMapper: ObjectMapper, payload: String?, clazz: Class<T>?): T {
    return objectMapper.readValue(payload, clazz)
}

fun <T> fromJson(objectMapper: ObjectMapper, payload: String?, clazz: TypeReference<T>?): T {
    return objectMapper.readValue(payload, clazz)
}

