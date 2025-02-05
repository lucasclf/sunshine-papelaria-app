package com.sunshine.backend.application.adapters

import com.sunshine.backend.domain.enums.OrderStatusEnum
import com.sunshine.backend.domain.models.OrderItemModel
import com.sunshine.backend.domain.models.OrderModel
import com.sunshine.backend.presentation.requests.*
import com.sunshine.backend.presentation.responses.OrderItemResponse
import com.sunshine.backend.presentation.responses.OrderResponse

object OrderAdapter {
    fun orderRequestToModel(orderRequest: OrderRequest): OrderModel {
        return OrderModel(
            clientId = orderRequest.clientId,
            totalValue = orderRequest.totalValue,
            status = OrderStatusEnum.AWAITING_PAYMENT,
            items = orderRequest.items.map { itemRequestToModel(it) }
        )
    }
    
    fun modelToOrderResponse(model: OrderModel): OrderResponse {
        return OrderResponse(
            id = model.id!!,
            clientId = model.clientId,
            totalValue = model.totalValue,
            status = model.status,
            items = model.items.map { itemModelToResponse(it)},
            freight = model.freight,
            discount = model.discount,
            trackingCode = model.trackingCode,
            carrierName = model.carrierName,
            paymentDate = model.paymentDate.toString(),
            sentDate = model.sentDate.toString(),
            createDate = model.createDate.toString(),
            updateDate = model.updateDate.toString()
        )
    }

    fun updatePaidRequestToModel(orderModel: OrderModel, updateRequest: OrderPaidUpdateRequest): OrderModel {
        return orderModel.copy(
            discount = updateRequest.discount,
            freight = updateRequest.freight,
            paymentDate = updateRequest.paymentDate
        )
    }

    fun updateSentRequestToModel(orderModel: OrderModel, updateRequest: OrderSentUpdateRequest): OrderModel {
        return orderModel.copy(
            carrierName = updateRequest.carrierName,
            trackingCode = updateRequest.trackingCode,
            sentDate = updateRequest.sentDate
        )
    }

    private fun itemRequestToModel(itemRequest: OrderItemRequest): OrderItemModel {
        return OrderItemModel(
            productId = itemRequest.productId,
            quantity = itemRequest.quantity
        )
    }

    private fun itemModelToResponse(orderItemModel: OrderItemModel): OrderItemResponse {
        return OrderItemResponse(
            orderId = orderItemModel.orderId!!,
            productId= orderItemModel.productId,
            quantity = orderItemModel.quantity,
            createDate = orderItemModel.createDate.toString()
        )
    }

    fun updateRefundedRequestToModel(model: OrderModel, update: OrderRefundedUpdateRequest): OrderModel {
        return model.copy(
            refundedValue = update.refundedValue,
            refundedReason = update.refundedReason,
            refundedDate = update.refundedDate
        )
    }
}