package com.sunshine.backend.domain.repositories

import com.sunshine.backend.domain.models.OrderModel
import com.sunshine.backend.domain.models.OrderPaidUpdateModel
import com.sunshine.backend.domain.models.OrderSentUpdateModel

interface OrderRepository {
    fun getAll(): List<OrderModel>
    fun getById(orderId: Int): OrderModel?
    fun insert(orderModel: OrderModel, value: Double): Int
    fun updateOrderToPaid(orderId: Int, update: OrderPaidUpdateModel): Boolean
    fun updateOrderToSent(orderId: Int, update: OrderSentUpdateModel): Boolean
    fun delete(orderId: Int): Boolean
}