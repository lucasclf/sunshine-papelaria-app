package com.sunshine.backend.presentation.requests

import com.sunshine.backend.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class OrderRefundedUpdateRequest(
    val refundedValue: Double,
    val refundedReason: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val refundedDate: LocalDateTime
)