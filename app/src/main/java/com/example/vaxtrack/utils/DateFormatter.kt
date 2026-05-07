package com.example.vaxtrack.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormatter {

    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    fun format(date: LocalDateTime): String {
        return date.format(formatter)
    }
}
