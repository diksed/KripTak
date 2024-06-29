package com.diksed.kriptak.utils

import android.annotation.SuppressLint
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@SuppressLint("NewApi")
fun formatDateTime(dateTime: String): String {
    val instant = Instant.parse(dateTime)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val datePart = localDateTime.format(dateFormatter)
    val timePart = localDateTime.format(timeFormatter)

    return "$datePart\n$timePart"
}