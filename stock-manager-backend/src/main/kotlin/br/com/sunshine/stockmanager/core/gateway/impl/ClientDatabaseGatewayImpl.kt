package br.com.sunshine.stockmanager.core.gateway.impl

import br.com.sunshine.stockmanager.core.adapters.CoreClientAdapter
import br.com.sunshine.stockmanager.core.repositories.ClientRepository
import br.com.sunshine.stockmanager.domain.enums.ClientStatusEnum
import br.com.sunshine.stockmanager.domain.enums.SunshineExceptionEnum
import br.com.sunshine.stockmanager.domain.exceptions.SunshineException
import br.com.sunshine.stockmanager.domain.gateway.ClientDatabaseGateway
import br.com.sunshine.stockmanager.domain.models.ClientModel
import org.springframework.stereotype.Component

@Component
class ClientDatabaseGatewayImpl(private val clientRepository: ClientRepository): ClientDatabaseGateway {
    override fun saveClient(client: ClientModel): ClientModel {
        val clientEntity = CoreClientAdapter.modelToEntity(client)

        val createdEntity = clientRepository.saveAndFlush(clientEntity)

        return CoreClientAdapter.entityToModel(createdEntity)
    }

    override fun findAllClients(): List<ClientModel> {
        val entityList = clientRepository.findAll()
        return entityList.map { CoreClientAdapter.entityToModel(it) }
    }

    override fun findById(id: Int): ClientModel? {
        val entity = clientRepository.findById(id)

        return entity.map { CoreClientAdapter.entityToModel(it) }.orElse(null)
    }

    override fun update(client: ClientModel): ClientModel {
        if (!clientRepository.existsById(client.id!!)) {
            throw SunshineException(SunshineExceptionEnum.CLIENT_NOT_FOUND)
        }

        val entity = CoreClientAdapter.modelToEntity(client) //TODO CORRIGIR ERRO DO CREATE_DATE
        val updatedEntity = clientRepository.saveAndFlush(entity)

        return CoreClientAdapter.entityToModel(updatedEntity)
    }

    override fun updateStatus(id: Int, status: ClientStatusEnum): ClientModel {
        val entity = clientRepository.findById(id)
            .orElseThrow { SunshineException(SunshineExceptionEnum.CLIENT_NOT_FOUND) }

        entity.status = status

        val updatedClient = clientRepository.saveAndFlush(entity)

        return CoreClientAdapter.entityToModel(updatedClient)
    }
}