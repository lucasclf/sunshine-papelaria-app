package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.enums.ClientStatusEnum
import com.sunshine.backend.domain.enums.SunshineExceptionEnum
import com.sunshine.backend.domain.exceptions.SunshineException
import com.sunshine.backend.domain.models.ClientModel
import com.sunshine.backend.domain.repositories.ClientRepository
import com.sunshine.backend.infra.adapters.ClientAdapter
import com.sunshine.backend.infra.database.tables.ClientEntity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import java.time.LocalDateTime


class ClientRepositoryImpl : ClientRepository {
    val logger = LoggerFactory.getLogger(this::class.java)

    override fun getAll(): List<ClientModel> = transaction {
        ClientEntity.selectAll().map(ClientAdapter::entityToModel)
    }

    override fun getById(clientId: Int): ClientModel {
        return findClientById(clientId) ?: throw SunshineException(
            SunshineExceptionEnum.CLIENT_NOT_FOUND,
            " ID: $clientId"
        ).also { logger.info("Cliente de id $clientId não localizado.") }
    }

    override fun insert(clientModel: ClientModel): ClientModel = transaction {
        val insertedId = ClientEntity.insert {
            it[name] = clientModel.name
            it[address] = clientModel.address
            it[cep] = clientModel.cep
            it[contact] = clientModel.contact
            it[status] = clientModel.status
        }[ClientEntity.id]

        findClientById(insertedId) ?: throw SunshineException(SunshineExceptionEnum.DATABASE_FAIL)
    }

    override fun update(clientModel: ClientModel): ClientModel = transaction {
        val updatedRows = ClientEntity.update({ ClientEntity.id eq clientModel.id!! }) {
            it[name] = clientModel.name
            it[address] = clientModel.address
            it[cep] = clientModel.cep
            it[contact] = clientModel.contact
            it[updateDate] = LocalDateTime.now()
        }

        findClientById(clientModel.id!!).also {
            if (updatedRows == 0) logger.info("Cliente de id ${clientModel.id} não encontrado após a atualização.")
        } ?: throw SunshineException(SunshineExceptionEnum.CLIENT_NOT_FOUND, " ID: ${clientModel.id}")
    }

    override fun changeClientStatus(clientId: Int, newStatus: ClientStatusEnum): ClientModel = transaction {
        val updatedRows = ClientEntity.update({ ClientEntity.id eq clientId }) {
            it[status] = newStatus
        }

        findClientById(clientId).also {
            if (updatedRows == 0) logger.info("Cliente de id $clientId não encontrado após a atualização.")
        } ?: throw SunshineException(SunshineExceptionEnum.CLIENT_NOT_FOUND, " ID: $clientId")
    }

    private fun findClientById(clientId: Int): ClientModel? = transaction {
        ClientEntity.selectAll().where { ClientEntity.id eq clientId }
            .map(ClientAdapter::entityToModel)
            .firstOrNull()
    }
}