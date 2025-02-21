package br.com.sunshine.stockmanager.api.responses

import br.com.sunshine.stockmanager.domain.enums.ProductStatusEnum

data class ProductResponse(
    val id: Int,
    val name: String,
    val price: Double,
    val stock: Int,
    val status: ProductStatusEnum,
    val createDate: String,
    val updateDate: String
)
