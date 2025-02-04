package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.enums.ClientStatusEnum
import com.sunshine.backend.domain.enums.SunshineExceptionEnum
import com.sunshine.backend.domain.exceptions.SunshineException
import com.sunshine.backend.domain.models.ClientModel
import com.sunshine.backend.domain.repositories.ClientRepository
import com.sunshine.backend.infra.database.tables.Clients
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import java.time.LocalDateTime


class ClientRepositoryImpl : ClientRepository {
    val logger = LoggerFactory.getLogger(this::class.java)

    override fun getAll(): List<ClientModel> = transaction {
        Clients.selectAll().map {
            ClientModel(
                id = it[Clients.id],
                name = it[Clients.name],
                address = it[Clients.address],
                cep = it[Clients.cep],
                contact = it[Clients.contact],
                status = it[Clients.status],
                createDate = it[Clients.createDate],
                updateDate = it[Clients.updateDate]
            )
        }
    }

    override fun getById(clientId: Int): ClientModel = transaction {
        Clients.selectAll().where { Clients.id eq clientId }
            .map {
                ClientModel(
                    id = it[Clients.id],
                    name = it[Clients.name],
                    address = it[Clients.address],
                    cep = it[Clients.cep],
                    contact = it[Clients.contact],
                    status = it[Clients.status],
                    createDate = it[Clients.createDate],
                    updateDate = it[Clients.updateDate]
                )
            }
    }.singleOrNull() ?: run {
        logger.info("Cliente de id ${clientId} não localizado.")
        throw SunshineException(SunshineExceptionEnum.CLIENT_NOT_FOUND, " ID: ${clientId}")
    }

    override fun insert(clientModel: ClientModel): ClientModel = transaction {
        try{
            val insertedRow = Clients.insert {
                it[name] = clientModel.name
                it[address] = clientModel.address
                it[cep] = clientModel.cep
                it[contact] = clientModel.contact
                it[status] = clientModel.status
            }.resultedValues?.firstOrNull()

            return@transaction insertedRow?.let { row ->
                ClientModel(
                    id = row[Clients.id],
                    name = row[Clients.name],
                    address = row[Clients.address],
                    cep = row[Clients.cep],
                    contact = row[Clients.contact],
                    status = row[Clients.status],
                    createDate = row[Clients.createDate],
                    updateDate = row[Clients.updateDate]
                )
            } ?: throw SunshineException(SunshineExceptionEnum.DATABASE_FAIL)
        } catch(e: Exception){
            logger.info("Falha ao criar o cliente ${clientModel.name}. Causa: ${e.message}")
            throw SunshineException(SunshineExceptionEnum.DATABASE_FAIL)
        }
    }

    override fun update(clientModel: ClientModel): ClientModel = transaction {
        Clients.update({ Clients.id eq clientModel.id!! }) {
            it[name] = clientModel.name
            it[address] = clientModel.address
            it[cep] = clientModel.cep
            it[contact] = clientModel.contact
            it[updateDate] = LocalDateTime.now()
        }

        return@transaction Clients.selectAll().where { Clients.id eq clientModel.id!! }
            .map { row ->
                ClientModel(
                    id = row[Clients.id],
                    name = row[Clients.name],
                    address = row[Clients.address],
                    cep = row[Clients.cep],
                    status = row[Clients.status],
                    contact = row[Clients.contact],
                    createDate = row[Clients.createDate],
                    updateDate = row[Clients.updateDate]
                )
            }.firstOrNull() ?: run {
            logger.info("Cliente de id ${clientModel.id} não encontrado após a atualização.")
            throw SunshineException(SunshineExceptionEnum.CLIENT_NOT_FOUND, " ID: ${clientModel.id}")
        }
    }

    override fun changeClientStatus(clientId: Int, newStatus: ClientStatusEnum): ClientModel = transaction {
        Clients.update({ Clients.id eq clientId }) {
            it[status] = newStatus
        }

        return@transaction Clients.selectAll().where { Clients.id eq clientId }
            .map { row ->
                ClientModel(
                    id = row[Clients.id],
                    name = row[Clients.name],
                    address = row[Clients.address],
                    cep = row[Clients.cep],
                    status = row[Clients.status],
                    contact = row[Clients.contact],
                    createDate = row[Clients.createDate],
                    updateDate = row[Clients.updateDate]
                )
            }.firstOrNull() ?: run {
            logger.info("Cliente de id ${clientId} não encontrado após a atualização.")
            throw SunshineException(SunshineExceptionEnum.CLIENT_NOT_FOUND, " ID: ${clientId}")
        }
    }
}