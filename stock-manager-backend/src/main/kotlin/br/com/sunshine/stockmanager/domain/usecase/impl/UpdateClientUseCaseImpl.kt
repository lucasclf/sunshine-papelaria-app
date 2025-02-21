package br.com.sunshine.stockmanager.domain.usecase.impl

import br.com.sunshine.stockmanager.domain.enums.ClientStatusEnum
import br.com.sunshine.stockmanager.domain.gateway.ClientDatabaseGateway
import br.com.sunshine.stockmanager.domain.models.ClientModel
import br.com.sunshine.stockmanager.domain.usecase.UpdateClientUseCase
import org.springframework.stereotype.Component

@Component
class UpdateClientUseCaseImpl(
    private val clientGateway: ClientDatabaseGateway
): UpdateClientUseCase {
    override fun update(client: ClientModel): ClientModel {
        return clientGateway.update(client)
    }

    override fun updateStatus(id: Int, status: ClientStatusEnum): ClientModel {
        return clientGateway.updateStatus(id, status)
    }
}