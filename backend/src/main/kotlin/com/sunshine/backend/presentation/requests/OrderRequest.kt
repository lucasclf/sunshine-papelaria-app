package com.sunshine.backend.presentation.requests

data class OrderRequest(
    val clientId: Int,
    val totalValue: Double,
    val items: List<OrderItemRequest>
)