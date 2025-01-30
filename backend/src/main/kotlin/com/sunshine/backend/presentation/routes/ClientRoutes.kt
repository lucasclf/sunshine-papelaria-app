package com.sunshine.backend.presentation.routes

import com.sunshine.backend.application.services.ClientService
import com.sunshine.backend.domain.models.Client
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Route.clientRoutes(service: ClientService) {
    route("/clients") {
        get {
            call.respond(service.getAllClients())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val client = service.getClientById(id)
                if (client != null) call.respond(client) else call.respond("Cliente não encontrado")
            } else {
                call.respond("ID inválido")
            }
        }

        post {
            val client = call.receive<Client>()
            val id = service.createClient(client)
            call.respond("Cliente inserido com ID $id")
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val client = call.receive<Client>().copy(id = id)
                val updated = service.updateClient(client)
                call.respond(if (updated) "Cliente atualizado" else "Cliente não encontrado")
            } else {
                call.respond("ID inválido")
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val deleted = service.deleteClient(id)
                call.respond(if (deleted) "Cliente removido" else "Cliente não encontrado")
            } else {
                call.respond("ID inválido")
            }
        }
    }
}