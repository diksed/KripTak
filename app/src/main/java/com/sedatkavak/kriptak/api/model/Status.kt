package com.sedatkavak.kriptak.api.model

data class Status(
    val creditCount: Int,
    val elapsed: String,
    val errorCode: String,
    val errorMessage: String,
    val timeStamp: String
)
