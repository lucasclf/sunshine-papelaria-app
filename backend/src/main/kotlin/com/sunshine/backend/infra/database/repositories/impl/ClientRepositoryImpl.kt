package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.models.Client
import com.sunshine.backend.domain.repositories.ClientRepository
import com.sunshine.backend.infra.database.Clients
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ClientRepositoryImpl : ClientRepository {
    override fun getAll(): List<Client> = transaction {
        Clients.selectAll().map {
            Client(
                id = it[Clients.id],
                name = it[Clients.name],
                address = it[Clients.address],
                cep = it[Clients.cep],
                contact = it[Clients.contact]
            )
        }
    }

    override fun getById(clientId: Int): Client? = transaction {
        Clients.selectAll().where { Clients.id eq clientId }
            .map {
                Client(
                    id = it[Clients.id],
                    name = it[Clients.name],
                    address = it[Clients.address],
                    cep = it[Clients.cep],
                    contact = it[Clients.contact]
                )
            }
            }
            .singleOrNull()

    override fun insert(client: Client): Int = transaction {
        Clients.insert {
            it[name] = client.name
            it[address] = client.address
            it[cep] = client.cep
            it[contact] = client.contact
        } get Clients.id
    }

    override fun update(client: Client): Boolean = transaction {
        Clients.update({ Clients.id eq client.id }) {
            it[name] = client.name
            it[address] = client.address
            it[cep] = client.cep
            it[contact] = client.contact
        } > 0
    }

    override fun delete(clientId: Int): Boolean = transaction {
        Clients.deleteWhere { id eq clientId } > 0
    }
}