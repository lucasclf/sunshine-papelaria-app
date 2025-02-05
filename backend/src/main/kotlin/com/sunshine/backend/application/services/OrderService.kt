package com.sunshine.backend.application.services

import com.sunshine.backend.application.adapters.OrderAdapter
import com.sunshine.backend.domain.enums.OrderStatusEnum
import com.sunshine.backend.domain.enums.SunshineExceptionEnum
import com.sunshine.backend.domain.exceptions.SunshineException
import com.sunshine.backend.domain.models.OrderModel
import com.sunshine.backend.domain.models.OrderItemModel
import com.sunshine.backend.domain.repositories.OrderItemRepository
import com.sunshine.backend.domain.repositories.OrderRepository
import com.sunshine.backend.domain.repositories.ProductRepository
import com.sunshine.backend.presentation.requests.OrderPaidUpdateRequest
import com.sunshine.backend.presentation.requests.OrderRefundedUpdateRequest
import com.sunshine.backend.presentation.requests.OrderRequest
import com.sunshine.backend.presentation.requests.OrderSentUpdateRequest
import com.sunshine.backend.presentation.responses.OrderResponse
import org.jetbrains.exposed.sql.transactions.transaction

class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val productRepository: ProductRepository,
    ) {
    fun getAllOrders(): List<OrderResponse> {
        return orderRepository.getAll().map{orderModel ->
            OrderAdapter.modelToOrderResponse(orderModel)
        }
    }

    fun getOrder(orderId: Int): OrderResponse{
        return OrderAdapter.modelToOrderResponse(orderRepository.getById(orderId))
    }

    fun createOrder(orderRequest: OrderRequest): OrderResponse {
        val orderModel = OrderAdapter.orderRequestToModel(orderRequest)

        val itemsValue = orderModel.items.sumOf { item ->
            val product = productRepository.getById(item.productId)
            product.price * item.quantity
        }

        return transaction {
            val persistedModel: OrderModel = orderRepository.insert(orderModel, itemsValue)

            val persistedItems: List<OrderItemModel> = orderItemRepository.insert(
                persistedModel.id!!,
                orderModel.items
            )

            return@transaction OrderAdapter.modelToOrderResponse(
                persistedModel.copy(items = persistedItems)
            )
        }
    }

    fun updateOrderToPaid(orderId: Int, update: OrderPaidUpdateRequest): OrderResponse {
        val orderModel = OrderAdapter.updatePaidRequestToModel(orderRepository.getById(orderId), update)

        if(orderModel.status == OrderStatusEnum.AWAITING_PAYMENT){
            val updatedOrder = orderRepository.updateOrderStatus(orderModel.copy(
                status = OrderStatusEnum.PAID
            ))
            return OrderAdapter.modelToOrderResponse(updatedOrder)
        } else {
            throw SunshineException(
                SunshineExceptionEnum.INVALID_ORDER_STATUS,
                OrderStatusEnum.AWAITING_PAYMENT.name
            )
        }
    }

    fun updateOrderToSent(orderId: Int, update: OrderSentUpdateRequest): OrderResponse {
        val orderModel = OrderAdapter.updateSentRequestToModel(orderRepository.getById(orderId), update)

        if(orderModel.status == OrderStatusEnum.PAID){
            val updatedOrder = orderRepository.updateOrderStatus(orderModel.copy(
                status = OrderStatusEnum.SENT
            ))
            return OrderAdapter.modelToOrderResponse(updatedOrder)
        } else {
            throw SunshineException(
                SunshineExceptionEnum.INVALID_ORDER_STATUS,
                OrderStatusEnum.PAID.name
            )
        }
    }

    fun updateOrderToReceived(orderId: Int): OrderResponse {
        val orderModel = orderRepository.getById(orderId)

        if(orderModel.status == OrderStatusEnum.SENT){
            val updatedOrder = orderRepository.updateOrderStatus(orderModel.copy(
                status = OrderStatusEnum.RECEIVED
            ))
            return OrderAdapter.modelToOrderResponse(updatedOrder)
        } else {
            throw SunshineException(
                SunshineExceptionEnum.INVALID_ORDER_STATUS,
                OrderStatusEnum.SENT.name
            )
        }
    }

    fun updateOrderToCanceled(orderId: Int): OrderResponse {
        val orderModel = orderRepository.getById(orderId)

        if(orderModel.status == OrderStatusEnum.AWAITING_PAYMENT){
            val updatedOrder = orderRepository.updateOrderStatus(orderModel.copy(
                status = OrderStatusEnum.CANCELED
            ))
            return OrderAdapter.modelToOrderResponse(updatedOrder)
        } else {
            throw SunshineException(
                SunshineExceptionEnum.INVALID_ORDER_STATUS,
                OrderStatusEnum.AWAITING_PAYMENT.name
            )
        }
    }

    fun updateOrderToRefunded(orderId: Int, update: OrderRefundedUpdateRequest): OrderResponse {
        val orderModel = OrderAdapter.updateRefundedRequestToModel(orderRepository.getById(orderId), update)

        if(orderModel.status == OrderStatusEnum.SENT || orderModel.status == OrderStatusEnum.PAID){
            val updatedOrder = orderRepository.updateOrderStatus(orderModel.copy(
                status = OrderStatusEnum.REFUNDED
            ))
            return OrderAdapter.modelToOrderResponse(updatedOrder)
        } else {
            throw SunshineException(
                SunshineExceptionEnum.INVALID_ORDER_STATUS,
                "${OrderStatusEnum.PAID.name} ou ${OrderStatusEnum.SENT.name}"
            )
        }
    }
}