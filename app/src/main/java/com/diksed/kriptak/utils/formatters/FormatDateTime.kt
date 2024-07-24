package com.diksed.kriptak.utils.formatters

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateTime: String): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
    val date = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME)
    return date.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatTime(dateTime: String): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    val time = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME)
    return time.format(formatter)
}