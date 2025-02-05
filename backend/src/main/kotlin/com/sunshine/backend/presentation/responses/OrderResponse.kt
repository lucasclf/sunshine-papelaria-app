package com.sunshine.backend.presentation.responses

import com.sunshine.backend.domain.enums.OrderStatusEnum

data class OrderResponse(
    val id: Int,
    val clientId: Int,
    val totalValue: Double,
    val status: OrderStatusEnum,
    val items: List<OrderItemResponse>,
    val freight: Double = 0.0,
    val discount: Double = 0.0,
    val trackingCode: String? = null,
    val carrierName: String? =null,
    val paymentDate: String? = null,
    val sentDate: String? = null,
    val createDate: String,
    val updateDate: String
)
