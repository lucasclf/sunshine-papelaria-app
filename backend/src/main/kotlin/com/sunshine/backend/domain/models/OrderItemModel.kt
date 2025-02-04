package com.sunshine.backend.domain.models

import com.sunshine.backend.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class OrderItemModel(
    val orderId: Int? = null,
    val productId: Int,
    val quantity: Int,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createDate: LocalDateTime? = null
)
