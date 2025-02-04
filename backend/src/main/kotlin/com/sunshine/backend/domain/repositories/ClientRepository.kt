package com.sunshine.backend.domain.repositories

import com.sunshine.backend.domain.enums.ClientStatusEnum
import com.sunshine.backend.domain.models.ClientModel

interface ClientRepository {
    fun getAll(): List<ClientModel>
    fun getById(clientId: Int): ClientModel
    fun insert(clientModel: ClientModel): ClientModel
    fun update(clientModel: ClientModel): ClientModel
    fun changeClientStatus(clientId: Int, newStatus: ClientStatusEnum): ClientModel
}