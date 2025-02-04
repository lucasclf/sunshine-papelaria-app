package com.sunshine.backend.domain.enums

import io.ktor.http.*

enum class SunshineExceptionEnum(
    val code: String,
    val httpCode: HttpStatusCode,
    val description: String
) {
    CLIENT_NOT_FOUND("SUN001", HttpStatusCode.NotFound, "O cliente não foi encontrado."),
    PRODUCT_NOT_FOUND("SUN002", HttpStatusCode.NotFound, "O produto não foi encontrado."),
    ORDER_NOT_FOUND("SUN003", HttpStatusCode.NotFound, "O pedido não foi encontrado."),
    ITEM_NOT_FOUND("SUN004", HttpStatusCode.NotFound, "O item não foi encontrado."),
    INVALID_ORDER_STATUS("SUN005", HttpStatusCode.BadRequest, "Essa operação exige que o status do pedido seja "),
    INVALID_CLIENT_STATUS("SUN006", HttpStatusCode.BadRequest, "Status de cliente inválido: "),
    INVALID_PRODUCT_STATUS("SUN007", HttpStatusCode.BadRequest, "Status de produto inválido: "),
    INVALID_ID("SUN008", HttpStatusCode.BadRequest, "O ID deve ser um numero inteiro."),
    DATABASE_FAIL("SUN009", HttpStatusCode.InternalServerError, "Falha no Banco de Dados."),
    INTERNAL_ERROR("SUN500", HttpStatusCode.InternalServerError, "Erro interno do servidor."),
}
