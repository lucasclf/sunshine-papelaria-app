package br.com.sunshine.stockmanager.api.controllers

import br.com.sunshine.stockmanager.api.adapters.ApiClientAdapter
import br.com.sunshine.stockmanager.api.requests.clients.ClientRequest
import br.com.sunshine.stockmanager.api.requests.clients.UpdateClientStatusRequest
import br.com.sunshine.stockmanager.api.responses.ClientResponse
import br.com.sunshine.stockmanager.api.responses.ResponseData
import br.com.sunshine.stockmanager.domain.usecase.CreateClientUseCase
import br.com.sunshine.stockmanager.domain.usecase.FindClientsUseCase
import br.com.sunshine.stockmanager.domain.usecase.UpdateClientUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping("clients")
class ClientController(
    val createClientUseCase: CreateClientUseCase,
    val findClientsUseCase: FindClientsUseCase,
    val updateClientUseCase: UpdateClientUseCase
) {

    @PostMapping
    fun createClient(
        @Valid @RequestBody request: ClientRequest
    ): ResponseData<ClientResponse> {
        val createdClient = createClientUseCase.create(ApiClientAdapter.createClientToModel(request))
        val response = ApiClientAdapter.clientModelToClientResponse(createdClient)

        return ResponseData(response)
    }

    @GetMapping
    fun findAllClients() : ResponseData<List<ClientResponse>> {
        val clientList = findClientsUseCase.findAll()
        val response = clientList.map { ApiClientAdapter.clientModelToClientResponse(it) }

        return ResponseData(response)
    }

    @GetMapping("/{id}")
    fun findClientById(
        @PathVariable id: Int
    ) : ResponseData<ClientResponse> {
        val client = findClientsUseCase.findById(id)
        val response = ApiClientAdapter.clientModelToClientResponse(client)

        return ResponseData(response)
    }

    @PatchMapping("/{id}")
    fun updateClientById(
        @PathVariable id: Int,
        @Valid @RequestBody request: ClientRequest
    ) : ResponseData<ClientResponse> {
        val client = ApiClientAdapter.createClientToModel(request).copy(id = id)
        val clientUpdated = updateClientUseCase.update(client)
        val response = ApiClientAdapter.clientModelToClientResponse(clientUpdated)

        return ResponseData(response)
    }

    @PatchMapping("/{id}/status")
    fun updateClientStatus(
        @PathVariable id: Int,
        @Valid @RequestBody request: UpdateClientStatusRequest
    ) : ResponseData<ClientResponse> {
        val clientUpdated = updateClientUseCase.updateStatus(id, request.status)
        val response = ApiClientAdapter.clientModelToClientResponse(clientUpdated)

        return ResponseData(response)
    }
}