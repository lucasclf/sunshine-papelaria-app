package com.sunshine.backend.application.adapters

import com.sunshine.backend.presentation.requests.ClientRequest
import com.sunshine.backend.presentation.responses.ClientResponse
import com.sunshine.backend.domain.enums.ClientStatusEnum
import com.sunshine.backend.domain.models.ClientModel

class ClientAdapter {
    companion object {
        fun requestToModel(request: ClientRequest, id: Int? = null): ClientModel{
            return ClientModel(
                id = id,
                name = request.name,
                cep = request.cep,
                status = ClientStatusEnum.ACTIVE,
                address = request.address,
                contact = request.contact,
            )
        }

        fun modelToResponse(model: ClientModel): ClientResponse {
            return ClientResponse(
                id = model.id!!,
                name = model.name,
                cep = model.cep,
                status = model.status,
                address = model.address,
                contact = model.contact,
                createdDate = model.createDate!!.toString(),
                updatedDate = model.updateDate!!.toString()
            )
        }
    }
}