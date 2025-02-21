package br.com.sunshine.stockmanager.domain.enums

import org.springframework.http.HttpStatus

enum class SunshineExceptionEnum(
    val code: String,
    val httpCode: HttpStatus,
    val description: String
) {
    CLIENT_NOT_FOUND("SUN001", HttpStatus.NOT_FOUND, "O cliente não foi encontrado."),
    PRODUCT_NOT_FOUND("SUN002", HttpStatus.NOT_FOUND, "O produto não foi encontrado."),
    ORDER_NOT_FOUND("SUN003", HttpStatus.NOT_FOUND, "O pedido não foi encontrado."),
    ITEM_NOT_FOUND("SUN004", HttpStatus.NOT_FOUND, "O item não foi encontrado."),
    INVALID_ORDER_STATUS("SUN005", HttpStatus.BAD_REQUEST, "Essa operação exige que o status do pedido seja "),
    INVALID_CLIENT_STATUS("SUN006", HttpStatus.BAD_REQUEST, "Status de cliente inválido ou nulo."),
    INVALID_PRODUCT_STATUS("SUN007", HttpStatus.BAD_REQUEST, "Status de produto inválido ou nulo."),
    INVALID_ID("SUN008", HttpStatus.BAD_REQUEST, "O ID deve ser um numero inteiro."),
    DATABASE_FAIL("SUN009", HttpStatus.INTERNAL_SERVER_ERROR, "Falha no Banco de Dados."),
    INSUFFICIENT_STOCK("SUN010", HttpStatus.UNPROCESSABLE_ENTITY, "Estoque insuficiente para o produto."),
    INTERNAL_ERROR("SUN500", HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor."),
}