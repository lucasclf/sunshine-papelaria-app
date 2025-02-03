package com.sunshine.backend.application.services

import com.sunshine.backend.domain.enums.OrderStatus
import com.sunshine.backend.domain.enums.SunshineExceptionEnum
import com.sunshine.backend.domain.exceptions.SunshineException
import com.sunshine.backend.domain.models.Order
import com.sunshine.backend.domain.models.OrderItem
import com.sunshine.backend.domain.models.OrderPaidUpdate
import com.sunshine.backend.domain.models.OrderSentUpdate
import com.sunshine.backend.domain.repositories.OrderItemRepository
import com.sunshine.backend.domain.repositories.OrderRepository
import com.sunshine.backend.domain.repositories.ProductRepository
import org.jetbrains.exposed.sql.transactions.transaction

class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val productRepository: ProductRepository,
    ) {
    fun getAllOrders(): List<Order> = orderRepository.getAll()

    fun getOrder(orderId: Int): Order? = orderRepository.getById(orderId)

    fun createOrder(order: Order): Int {
        val itemsValue = order.items.sumOf { item ->
            val product = productRepository.getById(item.productId)
            product!!.price * item.quantity
        }

        return transaction {
            val orderId: Int = orderRepository.insert(order, itemsValue)

            orderItemRepository.insert(orderId, order.items)

            return@transaction orderId
        }
    }

    fun updateOrderToPaid(orderId: Int, update: OrderPaidUpdate): Boolean {
        val order = getOrder(orderId)
        if(order!!.status == OrderStatus.AWAITING_PAYMENT){
            return orderRepository.updateOrderToPaid(orderId, update)
        } else {
            throw SunshineException(
                SunshineExceptionEnum.INVALID_ORDER_STATUS,
                OrderStatus.AWAITING_PAYMENT.name
            )
        }
    }

    fun updateOrderToSent(orderId: Int, update: OrderSentUpdate): Boolean {
        val order = getOrder(orderId)
        if(order!!.status == OrderStatus.PAID){
            return orderRepository.updateOrderToSent(orderId, update)
        } else {
            throw SunshineException(
                SunshineExceptionEnum.INVALID_ORDER_STATUS,
                OrderStatus.PAID.name
            )
        }
    }

    fun deleteOrder(orderId: Int): Boolean {
        val order = getOrder(orderId)
        if(order!!.status == OrderStatus.AWAITING_PAYMENT){
            return orderRepository.delete(orderId)
        } else {
            throw SunshineException(
                SunshineExceptionEnum.INVALID_ORDER_STATUS,
                OrderStatus.AWAITING_PAYMENT.name
            )
        }
    }

    fun getAllOrderItems(): List<OrderItem> = orderItemRepository.getAll()
    fun getAllItems(orderId: Int): List<OrderItem> = orderItemRepository.getByOrderId(orderId)
}