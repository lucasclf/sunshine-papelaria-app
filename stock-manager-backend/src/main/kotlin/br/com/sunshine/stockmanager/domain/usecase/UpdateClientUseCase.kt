package br.com.sunshine.stockmanager.domain.usecase

import br.com.sunshine.stockmanager.domain.enums.ClientStatusEnum
import br.com.sunshine.stockmanager.domain.models.ClientModel

interface UpdateClientUseCase {
    fun update(client: ClientModel): ClientModel
    fun updateStatus(id: Int, status: ClientStatusEnum): ClientModel
}