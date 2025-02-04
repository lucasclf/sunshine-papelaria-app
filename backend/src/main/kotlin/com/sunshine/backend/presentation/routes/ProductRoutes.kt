package com.sunshine.backend.presentation.routes

import com.sunshine.backend.application.services.ProductService
import com.sunshine.backend.presentation.requests.ProductRequest
import com.sunshine.backend.presentation.utils.ValidationUtils.validateId
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
            validateId(id)

            val product = service.getProductById(id!!)
            call.respond(product)
        }

        post {
            val productRequest = call.receive<ProductRequest>()
            val persistedProduct = service.createProduct(productRequest)
            call.respond(persistedProduct)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            validateId(id)

            val productRequest = call.receive<ProductRequest>()
            val updatedProduct = service.updateProduct(productRequest, id!!)
            call.respond(updatedProduct)

        }

        patch("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            validateId(id)

            val status = call.request.headers["status"]
            val updatedProduct = service.updateProductStatus(id!!, status)
            call.respond(updatedProduct)
        }
    }
}