package com.sunshine.backend.presentation.routes

import com.sunshine.backend.application.services.OrderService
import com.sunshine.backend.domain.enums.OrderStatus
import com.sunshine.backend.domain.models.Order
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Route.orderRoutes(service: OrderService) {
    route("/orders") {
        get {
            call.respond(service.getAllOrders())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val order = service.getOrder(id)
                if (order != null) call.respond(order) else call.respond("Pedido não encontrado")
            } else {
                call.respond("ID inválido")
            }
        }

        post {
            val order = call.receive<Order>()
            val id = service.createOrder(order)
            call.respond("Pedido inserido com ID $id")
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val orderStatus = call.receive<OrderStatus>()
                val updated = service.updateOrder(id, orderStatus)
                call.respond(if (updated) "Pedido atualizado" else "Pedido não encontrado")
            } else {
                call.respond("ID inválido")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val deleted = service.deleteOrder(id)
                call.respond(if (deleted) "Pedido removido" else "Pedido não encontrado")
            } else {
                call.respond("ID inválido")
            }
        }
    }
}