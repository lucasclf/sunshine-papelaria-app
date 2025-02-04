package com.sunshine.backend.domain.models

import com.sunshine.backend.domain.enums.ClientStatusEnum
import com.sunshine.backend.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ClientModel(
    val id: Int? = null,
    val name: String,
    val address: String,
    val cep: String,
    val contact: String,
    val status: ClientStatusEnum,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createDate: LocalDateTime? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updateDate: LocalDateTime? = null
)
