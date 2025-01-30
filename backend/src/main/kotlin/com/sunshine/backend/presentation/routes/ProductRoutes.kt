package com.sunshine.backend.presentation.routes

import com.sunshine.backend.application.services.ProductService
import com.sunshine.backend.domain.models.Product
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Route.productRoutes(service: ProductService) {
    route("/products") {
        get {
            call.respond(service.getAllProducts())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val product = service.getProductById(id)
                if (product != null) call.respond(product) else call.respond("Produto não encontrado")
            } else {
                call.respond("ID inválido")
            }
        }

        post {
            val product = call.receive<Product>()
            val id = service.createProduct(product)
            call.respond("Produto inserido com ID $id")
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val product = call.receive<Product>().copy(id = id)
                val updated = service.updateProduct(product)
                call.respond(if (updated) "Produto atualizado" else "Produto não encontrado")
            } else {
                call.respond("ID inválido")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val deleted = service.deleteProduct(id)
                call.respond(if (deleted) "Produto removido" else "Produto não encontrado")
            } else {
                call.respond("ID inválido")
            }
        }
    }
}