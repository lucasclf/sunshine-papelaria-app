package com.sunshine.backend.domain.repositories

import com.sunshine.backend.domain.models.Order
import com.sunshine.backend.domain.models.OrderPaidUpdate
import com.sunshine.backend.domain.models.OrderSentUpdate

interface OrderRepository {
    fun getAll(): List<Order>
    fun getById(orderId: Int): Order?
    fun insert(order: Order, value: Double): Int
    fun updateOrderToPaid(orderId: Int, update: OrderPaidUpdate): Boolean
    fun updateOrderToSent(orderId: Int, update: OrderSentUpdate): Boolean
    fun delete(orderId: Int): Boolean
}