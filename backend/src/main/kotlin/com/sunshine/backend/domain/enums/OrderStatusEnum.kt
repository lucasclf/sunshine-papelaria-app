package com.sunshine.backend.domain.enums

enum class OrderStatusEnum(val description: String) {
    AWAITING_PAYMENT("Aguardando pagamento"),
    PAID("Pago"),
    SENT("Enviado"),
    RECEIVED("Recebido"),
    REFUNDED("Reembolsado"),
    CANCELED("Cancelado")
}