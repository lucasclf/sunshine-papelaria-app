package com.sunshine.backend.presentation.routes

import com.sunshine.backend.application.services.OrderService
import kotlinx.serialization.json.Json
import com.sunshine.backend.domain.models.Order
import com.sunshine.backend.domain.models.OrderPaidUpdate
import com.sunshine.backend.domain.models.OrderSentUpdate
import io.ktor.http.*
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import org.slf4j.LoggerFactory


fun Route.orderRoutes(service: OrderService) {
    val logger = LoggerFactory.getLogger("com.sunshine.backend.presentation.routes.orderRoutes")

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

        patch("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@patch
            }

            val jsonString = call.receive<String>()
            val jsonUpdate = Json.decodeFromString<JsonObject>(jsonString)
            logger.info("Received JSON: $jsonUpdate")


            when {
                jsonUpdate.containsKey("paymentDate") -> {
                    val orderPaidUpdate = Json.decodeFromJsonElement<OrderPaidUpdate>(jsonUpdate)
                    val updated = service.updateOrderToPaid(id, orderPaidUpdate)
                    call.respond(if (updated) "Pedido atualizado" else "Pedido não encontrado")
                }
                jsonUpdate.containsKey("sentDate") -> {
                    val orderSentUpdate = Json.decodeFromJsonElement<OrderSentUpdate>(jsonUpdate)
                    val updated = service.updateOrderToSent(id, orderSentUpdate)
                    call.respond(if (updated) "Pedido atualizado" else "Pedido não encontrado")
                }
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