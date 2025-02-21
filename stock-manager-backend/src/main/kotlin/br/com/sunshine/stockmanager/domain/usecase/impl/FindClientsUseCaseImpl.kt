package br.com.sunshine.stockmanager.domain.usecase.impl

import br.com.sunshine.stockmanager.domain.enums.SunshineExceptionEnum
import br.com.sunshine.stockmanager.domain.exceptions.SunshineException
import br.com.sunshine.stockmanager.domain.gateway.ClientDatabaseGateway
import br.com.sunshine.stockmanager.domain.models.ClientModel
import br.com.sunshine.stockmanager.domain.usecase.FindClientsUseCase
import org.springframework.stereotype.Component

@Component
class FindClientsUseCaseImpl(
    private val clientGateway: ClientDatabaseGateway
): FindClientsUseCase {
    override fun findAll(): List<ClientModel> {
        return clientGateway.findAllClients()
    }

    override fun findById(id: Int): ClientModel {
        val client = clientGateway.findById(id)

        return client ?: throw SunshineException(SunshineExceptionEnum.CLIENT_NOT_FOUND)
    }
}