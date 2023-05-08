package com.kenshi.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun Instant.format(pattern: String, timeZone: TimeZone = TimeZone.currentSystemDefault()): String {
    val localDateTime = this.toLocalDateTime(timeZone)
    val formatter = DateTimePeriodFormatter(pattern)
    return formatter.format(localDateTime)
}

class DateTimePeriodFormatter(private val pattern: String) {
    private val formatTokens = listOf("yyyy", "MM", "dd", "HH", "mm")

    fun format(localDateTime: kotlinx.datetime.LocalDateTime): String {
        val components = mapOf(
            "yyyy" to localDateTime.year.toString(),
            "MM" to localDateTime.monthNumber.toString().padStart(2, '0'),
            "dd" to localDateTime.dayOfMonth.toString().padStart(2, '0'),
            "HH" to localDateTime.hour.toString().padStart(2, '0'),
            "mm" to localDateTime.minute.toString().padStart(2, '0'),
        )

        return formatTokens.fold(pattern) { acc, token ->
            acc.replace(token, components[token] ?: "")
        }
    }
}