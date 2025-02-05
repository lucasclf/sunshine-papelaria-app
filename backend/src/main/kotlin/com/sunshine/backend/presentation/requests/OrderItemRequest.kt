package com.sunshine.backend.presentation.requests

data class OrderItemRequest(
    val productId: Int,
    val quantity: Int
)
