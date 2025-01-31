package com.sunshine.backend.application.services

import com.sunshine.backend.domain.enums.OrderStatus
import com.sunshine.backend.domain.models.Order
import com.sunshine.backend.domain.models.OrderItem
import com.sunshine.backend.domain.repositories.OrderItemRepository
import com.sunshine.backend.domain.repositories.OrderRepository
import org.jetbrains.exposed.sql.transactions.transaction

class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository

) {
    fun getAllOrders(): List<Order> = orderRepository.getAll()
    fun getOrder(orderId: Int): Order? = orderRepository.getById(orderId)
    fun createOrder(order: Order): Int {
        return transaction {
            val orderId: Int = orderRepository.insert(order)

            orderItemRepository.insert(orderId, order.items)

            return@transaction orderId
        }
    }
    fun updateOrder(orderId: Int, status: OrderStatus) = orderRepository.updateStatus(orderId, status)
    fun deleteOrder(orderId: Int) = orderRepository.delete(orderId)

    fun getAllOrderItems(): List<OrderItem> = orderItemRepository.getAll()
    fun getAllItems(orderId: Int): List<OrderItem> = orderItemRepository.getByOrderId(orderId)
}