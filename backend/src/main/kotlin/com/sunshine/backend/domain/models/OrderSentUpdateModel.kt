package com.sunshine.backend.domain.models

import com.sunshine.backend.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class OrderSentUpdateModel(
    val carrierName: String,
    val trackingCode: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val sentDate: LocalDateTime
)