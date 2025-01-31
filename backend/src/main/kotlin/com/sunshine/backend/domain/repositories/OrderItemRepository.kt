package com.sunshine.backend.domain.repositories

import com.sunshine.backend.domain.models.OrderItem

interface OrderItemRepository {
    fun getAll(): List<OrderItem>
    fun getById(orderId: Int, productId: Int): OrderItem?
    fun getByOrderId(orderId: Int): List<OrderItem>
    fun insert(orderId: Int, orderItems: List<OrderItem>)
    fun update(orderItem: OrderItem): Boolean
    fun deleteItem(orderId: Int, productId: Int): Boolean
    fun deleteOrder(orderId: Int): Boolean
}