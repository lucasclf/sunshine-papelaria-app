package com.sunshine.backend.domain.models

import com.sunshine.backend.domain.enums.OrderStatusEnum
import com.sunshine.backend.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class OrderModel (
    val id: Int,
    val clientId: Int,
    val totalValue: Double = 0.0,
    val status: OrderStatusEnum = OrderStatusEnum.AWAITING_PAYMENT,
    val items: List<OrderItemModel>,
    val freight: Double = 0.0,
    val discount: Double = 0.0,
    val trackingCode: String? = null,
    val carrierName: String? =null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val paymentDate: LocalDateTime? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val sentDate: LocalDateTime? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createDate: LocalDateTime? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updateDate: LocalDateTime? = null
)