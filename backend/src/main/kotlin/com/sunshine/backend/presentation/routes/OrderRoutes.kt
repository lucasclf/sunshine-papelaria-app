package com.sunshine.backend.presentation.routes

import com.sunshine.backend.application.services.OrderService
import com.sunshine.backend.domain.enums.OrderStatusEnum
import com.sunshine.backend.presentation.requests.OrderPaidUpdateRequest
import com.sunshine.backend.presentation.requests.OrderRefundedUpdateRequest
import com.sunshine.backend.presentation.requests.OrderRequest
import com.sunshine.backend.presentation.requests.OrderSentUpdateRequest
import com.sunshine.backend.presentation.utils.ValidationUtils.validateId
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory


fun Route.orderRoutes(service: OrderService) {
    val logger = LoggerFactory.getLogger("com.sunshine.backend.presentation.routes.orderRoutes")

    route("/orders") {
        get {
            call.respond(service.getAllOrders())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            validateId(id)

            val order = service.getOrder(id!!)
            call.respond(order)
        }

        post {
            val orderRequest = call.receive<OrderRequest>()
            val persistedOrder = service.createOrder(orderRequest)
            call.respond(persistedOrder)
        }

        patch("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            validateId(id)

            val status = call.request.headers["status"]
            when {
                OrderStatusEnum.PAID.name == status -> {
                    val updatedRequest = call.receive<OrderPaidUpdateRequest>()
                    val updatedOrder = service.updateOrderToPaid(id!!, updatedRequest)
                    call.respond(updatedOrder)
                }
                OrderStatusEnum.SENT.name == status -> {
                    val updatedRequest = call.receive<OrderSentUpdateRequest>()
                    val updatedOrder = service.updateOrderToSent(id!!, updatedRequest)
                    call.respond(updatedOrder)
                }
                OrderStatusEnum.RECEIVED.name == status -> {
                    val updatedOrder = service.updateOrderToReceived(id!!)
                    call.respond(updatedOrder)
                }
                OrderStatusEnum.REFUNDED.name == status -> {
                    val updatedRequest = call.receive<OrderRefundedUpdateRequest>()
                    val updatedOrder = service.updateOrderToRefunded(id!!, updatedRequest)
                    call.respond(updatedOrder)
                }
                OrderStatusEnum.CANCELED.name == status -> {
                    val updatedOrder = service.updateOrderToCanceled(id!!)
                    call.respond(updatedOrder)
                }
            }
        }
    }
}