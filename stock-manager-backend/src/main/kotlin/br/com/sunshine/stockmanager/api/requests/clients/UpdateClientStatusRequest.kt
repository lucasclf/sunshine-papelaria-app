package br.com.sunshine.stockmanager.api.requests.clients

import br.com.sunshine.stockmanager.domain.enums.ClientStatusEnum

data class UpdateClientStatusRequest(
    val status: ClientStatusEnum
)
