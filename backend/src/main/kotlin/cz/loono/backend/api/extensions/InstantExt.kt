package cz.loono.backend.api.extensions

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Instant.toOffsetDateTime(): LocalDateTime = LocalDateTime.ofInstant(this, ZoneId.systemDefault())
