package com.sunshine.backend.domain.models

import com.sunshine.backend.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class OrderPaidUpdate(
    val discount: Double,
    val freight: Double,
    @Serializable(with = LocalDateTimeSerializer::class)
    val paymentDate: LocalDateTime
)