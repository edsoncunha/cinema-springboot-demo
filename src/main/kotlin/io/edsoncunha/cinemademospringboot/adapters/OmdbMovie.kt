package io.edsoncunha.cinemademospringboot.adapters

import java.time.ZoneId
import java.time.ZonedDateTime

data class OmdbMovie(
    val Title: String,
    val Year: Int,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Language: String,
    val Country: String,
    val Awards: String,
    val Poster: String,
    val Ratings: List<Ratings>,
    val Metascore: Int,
    val imdbRating: Double,
    val imdbVotes: String,
    val imdbID: String,
    val Type: String,
    val DVD: String,
    val BoxOffice: String,
    val Production: String,
    val Website: String,
    val Response: Boolean,
    val LastUpdate: ZonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"))
)

data class Ratings(
    val Source: String,
    val Value: String
)