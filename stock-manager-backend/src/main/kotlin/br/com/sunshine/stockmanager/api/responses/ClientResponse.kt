package br.com.sunshine.stockmanager.api.responses

import br.com.sunshine.stockmanager.domain.enums.ClientStatusEnum

data class ClientResponse(
    val id: Int,
    val name: String,
    val address: String,
    val cep: String,
    val contact: String,
    val status: ClientStatusEnum,
    val createdDate: String,
    val updatedDate: String
)
