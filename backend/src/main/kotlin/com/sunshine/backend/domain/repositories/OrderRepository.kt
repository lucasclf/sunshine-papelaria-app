package com.sunshine.backend.domain.repositories

import com.sunshine.backend.domain.enums.OrderStatus
import com.sunshine.backend.domain.models.Order

interface OrderRepository {
    fun getAll(): List<Order>
    fun getById(orderId: Int): Order?
    fun insert(order: Order): Int
    fun update(order: Order): Boolean
    fun updateStatus(orderId: Int, status: OrderStatus): Boolean
    fun delete(orderId: Int): Boolean
}