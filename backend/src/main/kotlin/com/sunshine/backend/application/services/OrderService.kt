package com.sunshine.backend.application.services

import com.sunshine.backend.domain.enums.OrderStatusEnum
import com.sunshine.backend.domain.enums.SunshineExceptionEnum
import com.sunshine.backend.domain.exceptions.SunshineException
import com.sunshine.backend.domain.models.OrderModel
import com.sunshine.backend.domain.models.OrderItemModel
import com.sunshine.backend.domain.models.OrderPaidUpdateModel
import com.sunshine.backend.domain.models.OrderSentUpdateModel
import com.sunshine.backend.domain.repositories.OrderItemRepository
import com.sunshine.backend.domain.repositories.OrderRepository
import com.sunshine.backend.domain.repositories.ProductRepository
import org.jetbrains.exposed.sql.transactions.transaction

class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val productRepository: ProductRepository,
    ) {
    fun getAllOrders(): List<OrderModel> = orderRepository.getAll()

    fun getOrder(orderId: Int): OrderModel? = orderRepository.getById(orderId)

    fun createOrder(orderModel: OrderModel): Int {
        val itemsValue = orderModel.items.sumOf { item ->
            val product = productRepository.getById(item.productId)
            product!!.price * item.quantity
        }

        return transaction {
            val orderId: Int = orderRepository.insert(orderModel, itemsValue)

            orderItemRepository.insert(orderId, orderModel.items)

            return@transaction orderId
        }
    }

    fun updateOrderToPaid(orderId: Int, update: OrderPaidUpdateModel): Boolean {
        val order = getOrder(orderId)
        if(order!!.status == OrderStatusEnum.AWAITING_PAYMENT){
            return orderRepository.updateOrderToPaid(orderId, update)
        } else {
            throw SunshineException(
                SunshineExceptionEnum.INVALID_ORDER_STATUS,
                OrderStatusEnum.AWAITING_PAYMENT.name
            )
        }
    }

    fun updateOrderToSent(orderId: Int, update: OrderSentUpdateModel): Boolean {
        val order = getOrder(orderId)
        if(order!!.status == OrderStatusEnum.PAID){
            return orderRepository.updateOrderToSent(orderId, update)
        } else {
            throw SunshineException(
                SunshineExceptionEnum.INVALID_ORDER_STATUS,
                OrderStatusEnum.PAID.name
            )
        }
    }

    fun deleteOrder(orderId: Int): Boolean {
        val order = getOrder(orderId)
        if(order!!.status == OrderStatusEnum.AWAITING_PAYMENT){
            return orderRepository.delete(orderId)
        } else {
            throw SunshineException(
                SunshineExceptionEnum.INVALID_ORDER_STATUS,
                OrderStatusEnum.AWAITING_PAYMENT.name
            )
        }
    }

    fun getAllOrderItems(): List<OrderItemModel> = orderItemRepository.getAll()
    fun getAllItems(orderId: Int): List<OrderItemModel> = orderItemRepository.getByOrderId(orderId)
}