package br.com.sunshine.stockmanager.domain.usecase.impl

import br.com.sunshine.stockmanager.domain.gateway.ClientDatabaseGateway
import br.com.sunshine.stockmanager.domain.models.ClientModel
import br.com.sunshine.stockmanager.domain.usecase.CreateClientUseCase
import org.springframework.stereotype.Component

@Component
class CreateClientUseCaseImpl(
    private val clientGateway: ClientDatabaseGateway
): CreateClientUseCase {
    override fun create(client: ClientModel): ClientModel {
        return clientGateway.saveClient(client)
    }
}