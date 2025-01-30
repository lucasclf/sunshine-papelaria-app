package com.sunshine.backend.application.services

import com.sunshine.backend.domain.models.Client
import com.sunshine.backend.domain.repositories.ClientRepository

class ClientService (private val repository: ClientRepository) {
    fun getAllClients(): List<Client> = repository.getAll()
    fun getClientById(id: Int): Client? = repository.getById(id)
    fun createClient(client: Client): Int = repository.insert(client)
    fun updateClient(client: Client): Boolean = repository.update(client)
    fun deleteClient(id: Int): Boolean = repository.delete(id)
}