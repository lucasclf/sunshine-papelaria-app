package com.sunshine.backend.presentation.requests

import com.sunshine.backend.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class OrderSentUpdateRequest(
    val carrierName: String,
    val trackingCode: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val sentDate: LocalDateTime
)
