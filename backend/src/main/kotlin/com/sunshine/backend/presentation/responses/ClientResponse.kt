package com.sunshine.backend.presentation.responses

import com.sunshine.backend.domain.enums.ClientStatusEnum

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