package com.sunshine.backend.presentation.requests

import com.sunshine.backend.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class OrderPaidUpdateRequest(
    val discount: Double,
    val freight: Double,
    @Serializable(with = LocalDateTimeSerializer::class)
    val paymentDate: LocalDateTime
)
