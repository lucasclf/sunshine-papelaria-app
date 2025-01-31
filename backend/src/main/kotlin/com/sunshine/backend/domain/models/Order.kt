package com.sunshine.backend.domain.models

import com.sunshine.backend.domain.enums.OrderStatus
import com.sunshine.backend.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Order (
    val id: Int,
    val clientId: Int,
    val totalValue: Double,
    val status: OrderStatus,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createDate: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updateDate: LocalDateTime,
)