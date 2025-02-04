package com.sunshine.backend.presentation.requests

data class ClientRequest(
    val name: String,
    val address: String,
    val cep: String,
    val contact: String
)
