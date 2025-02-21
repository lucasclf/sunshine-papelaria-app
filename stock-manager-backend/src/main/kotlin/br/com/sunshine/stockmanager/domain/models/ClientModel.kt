package br.com.sunshine.stockmanager.domain.models

import br.com.sunshine.stockmanager.domain.enums.ClientStatusEnum
import java.time.LocalDateTime

data class ClientModel(
    val id: Int? = null,
    val name: String,
    val address: String,
    val cep: String,
    val contact: String,
    val status: ClientStatusEnum,
    val createDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null
)
