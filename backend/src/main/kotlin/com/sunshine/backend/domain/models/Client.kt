package com.sunshine.backend.domain.models

import com.sunshine.backend.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Client(
    val id: Int,
    val name: String,
    val address: String,
    val cep: String,
    val contact: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createDate: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updateDate: LocalDateTime
)
