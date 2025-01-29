package com.sunshine.backend.routes

import com.sunshine.backend.database.ProductDAO
import com.sunshine.backend.models.Product
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Route.productRoutes() {
    route("/products") {
        get {
            call.respond(ProductDAO.getAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val product = ProductDAO.getById(id)
                if (product != null) call.respond(product) else call.respond("Produto não encontrado")
            } else {
                call.respond("ID inválido")
            }
        }

        post {
            val product = call.receive<Product>()
            val id = ProductDAO.insert(product)
            call.respond("Produto inserido com ID $id")
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val product = call.receive<Product>().copy(id = id)
                val updated = ProductDAO.update(product)
                call.respond(if (updated) "Produto atualizado" else "Produto não encontrado")
            } else {
                call.respond("ID inválido")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val deleted = ProductDAO.delete(id)
                call.respond(if (deleted) "Produto removido" else "Produto não encontrado")
            } else {
                call.respond("ID inválido")
            }
        }
    }
}