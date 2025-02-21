package br.com.sunshine.stockmanager.domain.usecase

import br.com.sunshine.stockmanager.domain.models.ClientModel

interface CreateClientUseCase {
    fun create(client: ClientModel): ClientModel
}