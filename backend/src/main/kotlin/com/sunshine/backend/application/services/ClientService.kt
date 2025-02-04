package com.sunshine.backend.application.services

import com.sunshine.backend.application.adapters.ClientAdapter
import com.sunshine.backend.domain.enums.ClientStatusEnum
import com.sunshine.backend.domain.enums.SunshineExceptionEnum
import com.sunshine.backend.domain.exceptions.SunshineException
import com.sunshine.backend.domain.repositories.ClientRepository
import com.sunshine.backend.presentation.requests.ClientRequest
import com.sunshine.backend.presentation.responses.ClientResponse
import org.slf4j.LoggerFactory

class ClientService (private val repository: ClientRepository) {
    val logger = LoggerFactory.getLogger(this::class.java)

    fun getAllClients(): List<ClientResponse> {

        return repository.getAll().map { clientModel ->
            ClientAdapter.modelToResponse(clientModel)
        }
    }

    fun getClientById(id: Int): ClientResponse = ClientAdapter.modelToResponse(repository.getById(id))

    fun createClient(clientRequest: ClientRequest): ClientResponse {
        val clientModel = repository.insert(ClientAdapter.requestToModel(clientRequest))
        return ClientAdapter.modelToResponse(clientModel)
    }

    fun updateClient(clientRequest: ClientRequest, id: Int): ClientResponse {
        val clientModel = repository.update(ClientAdapter.requestToModel(clientRequest, id))
        return ClientAdapter.modelToResponse(clientModel)
    }

    fun updateClientStatus(id: Int, status: String?): ClientResponse {
        val clientStatus = validateStatus(status)

        return ClientAdapter.modelToResponse(repository.changeClientStatus(id, clientStatus))
    }

    private fun validateStatus(status: String?): ClientStatusEnum {
        return try {
            ClientStatusEnum.valueOf(status!!)
        } catch (e: Exception) {
            logger.info("O status $status não é válido.")
            throw SunshineException(SunshineExceptionEnum.INVALID_CLIENT_STATUS)
        }
    }
}