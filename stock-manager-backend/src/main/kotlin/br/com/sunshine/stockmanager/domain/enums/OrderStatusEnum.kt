package br.com.sunshine.stockmanager.domain.enums

import br.com.sunshine.stockmanager.domain.exceptions.SunshineException

enum class OrderStatusEnum(val description: String) {
    AWAITING_PAYMENT("Aguardando pagamento"),
    PAID("Pago"),
    SENT("Enviado"),
    RECEIVED("Recebido"),
    REFUNDED("Reembolsado"),
    CANCELED("Cancelado");

    companion object {
        fun fromValue(value: String?): OrderStatusEnum {
            return OrderStatusEnum.entries.find { it.name == value } ?: throw SunshineException(
                SunshineExceptionEnum.INVALID_ORDER_STATUS
            )
        }
    }
}