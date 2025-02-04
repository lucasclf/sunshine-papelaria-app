package com.sunshine.backend.presentation.routes

import com.sunshine.backend.application.services.OrderService
import kotlinx.serialization.json.Json
import com.sunshine.backend.domain.models.OrderModel
import com.sunshine.backend.domain.models.OrderPaidUpdateModel
import com.sunshine.backend.domain.models.OrderSentUpdateModel
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
            val orderModel = call.receive<OrderModel>()
            val id = service.createOrder(orderModel)
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
                    val orderPaidUpdateModel = Json.decodeFromJsonElement<OrderPaidUpdateModel>(jsonUpdate)
                    val updated = service.updateOrderToPaid(id, orderPaidUpdateModel)
                    call.respond(if (updated) "Pedido atualizado" else "Pedido não encontrado")
                }
                jsonUpdate.containsKey("sentDate") -> {
                    val orderSentUpdateModel = Json.decodeFromJsonElement<OrderSentUpdateModel>(jsonUpdate)
                    val updated = service.updateOrderToSent(id, orderSentUpdateModel)
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