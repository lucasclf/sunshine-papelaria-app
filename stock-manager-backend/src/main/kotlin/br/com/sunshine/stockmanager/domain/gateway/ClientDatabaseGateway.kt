package br.com.sunshine.stockmanager.domain.gateway

import br.com.sunshine.stockmanager.domain.enums.ClientStatusEnum
import br.com.sunshine.stockmanager.domain.models.ClientModel

interface ClientDatabaseGateway {
    fun saveClient(client: ClientModel): ClientModel
    fun findAllClients(): List<ClientModel>
    fun findById(id: Int): ClientModel?
    fun update(client: ClientModel): ClientModel
    fun updateStatus(id: Int, status: ClientStatusEnum): ClientModel
}