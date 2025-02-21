package br.com.sunshine.stockmanager.api.adapters

import br.com.sunshine.stockmanager.api.requests.clients.ClientRequest
import br.com.sunshine.stockmanager.api.responses.ClientResponse
import br.com.sunshine.stockmanager.domain.enums.ClientStatusEnum
import br.com.sunshine.stockmanager.domain.models.ClientModel

object ApiClientAdapter {

    fun createClientToModel(request: ClientRequest): ClientModel =
        ClientModel(
            name = request.name,
            address = request.address,
            cep = request.cep,
            contact = request.contact,
            status = ClientStatusEnum.ACTIVE
        )

    fun clientModelToClientResponse(model: ClientModel): ClientResponse =
        ClientResponse(
            id = model.id!!,
            name = model.name,
            address = model.address,
            cep = model.cep,
            contact = model.contact,
            status = model.status,
            createdDate = model.createDate.toString(),
            updatedDate = model.updateDate.toString()
        )

}