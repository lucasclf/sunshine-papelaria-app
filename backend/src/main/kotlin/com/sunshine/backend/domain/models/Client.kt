package com.sunshine.backend.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Client(
    val id: Int,
    val name: String,
    val address: String,
    val cep: String,
    val contact: String
)
