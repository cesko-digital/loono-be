package cz.loono.backend.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

fun Instant.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(this, ZoneId.of("UTC"))
fun Instant.toLocalDate(): LocalDate = LocalDate.ofInstant(this, ZoneId.of("UTC"))
