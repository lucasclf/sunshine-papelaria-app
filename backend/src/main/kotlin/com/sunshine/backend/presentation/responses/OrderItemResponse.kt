package com.sunshine.backend.presentation.responses

data class OrderItemResponse(
    val orderId: Int,
    val productId: Int,
    val quantity: Int,
    val createDate: String
)
