package br.com.sunshine.stockmanager.api.requests.clients

import jakarta.validation.constraints.NotBlank

data class ClientRequest(
    @field:NotBlank val name: String,
    @field:NotBlank val address: String,
    @field:NotBlank val cep: String,
    @field:NotBlank val contact: String
)