package com.sunshine.backend.domain.repositories

import com.sunshine.backend.domain.models.Client

interface ClientRepository {
    fun getAll(): List<Client>
    fun getById(clientId: Int): Client?
    fun insert(client: Client): Int
    fun update(client: Client): Boolean
    fun delete(clientId: Int): Boolean
}