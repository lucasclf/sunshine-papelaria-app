package br.com.sunshine.stockmanager.core.adapters

import br.com.sunshine.stockmanager.core.entities.ClientEntity
import br.com.sunshine.stockmanager.domain.models.ClientModel

object CoreClientAdapter {
    fun entityToModel(entity: ClientEntity): ClientModel =
        ClientModel(
            id = entity.id,
            name = entity.name!!,
            address = entity.address!!,
            cep = entity.cep!!,
            contact = entity.contact!!,
            status = entity.status!!,
            createDate = entity.createDate,
            updateDate = entity.updateDate
        )

    fun modelToEntity(model: ClientModel): ClientEntity =
        ClientEntity(
            id = model.id,
            name = model.name,
            address = model.address,
            cep = model.cep,
            contact = model.contact,
            status = model.status
        )
}