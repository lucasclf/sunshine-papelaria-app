package br.com.sunshine.stockmanager.api.responses

data class ErrorResponse(
    val title: String,
    val code: String,
    val message: String
)