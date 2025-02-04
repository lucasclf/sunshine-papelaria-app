package com.sunshine.backend.domain.repositories

import com.sunshine.backend.domain.models.OrderItemModel

interface OrderItemRepository {
    fun getAll(): List<OrderItemModel>
    fun getById(orderId: Int, productId: Int): OrderItemModel?
    fun getByOrderId(orderId: Int): List<OrderItemModel>
    fun insert(orderId: Int, orderItemModels: List<OrderItemModel>)
    fun update(orderItemModel: OrderItemModel): Boolean
    fun deleteItem(orderId: Int, productId: Int): Boolean
    fun deleteOrder(orderId: Int): Boolean
}