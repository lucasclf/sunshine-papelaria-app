package com.sunshine.backend.presentation.routes

import com.sunshine.backend.application.services.ClientService
import com.sunshine.backend.presentation.requests.ClientRequest
import com.sunshine.backend.presentation.utils.ValidationUtils.validateId
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory


fun Route.clientRoutes(service: ClientService) {
    val logger = LoggerFactory.getLogger("com.sunshine.backend.presentation.routes.clientRoutes")

    route("/clients") {
        get {
            logger.info("Iniciando recuperação de todos os clientes!")
            call.respond(service.getAllClients())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            logger.info("Iniciando recuperação de cliente pelo id ${id}!")
            validateId(id)

            call.respond(service.getClientById(id!!))
        }

        post {
            logger.info("Iniciando criação de cliente!")
            val request = call.receive<ClientRequest>()
            call.respond(service.createClient(request))
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            logger.info("Iniciando atualização do cliente ${id}!")
            validateId(id)

            val request = call.receive<ClientRequest>()
            call.respond(service.updateClient(request, id!!))

        }

        patch("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            validateId(id)

            val status = call.request.headers["status"]

            logger.info("Iniciando exclusão do cliente ${id}!")

            call.respond(service.updateClientStatus(id!!, status))
        }
    }
}
