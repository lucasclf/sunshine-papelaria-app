package br.com.sunshine.stockmanager.domain.usecase

import br.com.sunshine.stockmanager.domain.models.ClientModel

interface FindClientsUseCase {
    fun findAll(): List<ClientModel>

    fun findById(id: Int): ClientModel
}