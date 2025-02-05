package com.sunshine.backend.domain.repositories

import com.sunshine.backend.domain.models.OrderModel

interface OrderRepository {
    fun getAll(): List<OrderModel>
    fun getById(orderId: Int): OrderModel
    fun insert(orderModel: OrderModel, value: Double): OrderModel
    fun updateOrderStatus(orderModel: OrderModel): OrderModel
}