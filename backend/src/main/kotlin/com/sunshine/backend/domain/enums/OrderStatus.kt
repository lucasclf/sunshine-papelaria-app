package com.sunshine.backend.domain.enums

enum class OrderStatus(val description: String) {
    AWAITING_PAYMENT("Aguardando pagamento"),
    PAID("Pago"),
    SENT("Enviado"),
    CANCELED("Cancelado")
}