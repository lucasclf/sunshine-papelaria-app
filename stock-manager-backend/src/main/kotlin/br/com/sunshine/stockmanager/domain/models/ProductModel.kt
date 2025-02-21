package br.com.sunshine.stockmanager.domain.models

import br.com.sunshine.stockmanager.domain.enums.ProductStatusEnum
import java.time.LocalDateTime

data class ProductModel(
    val id: Int? = null,
    val name: String,
    val price: Double,
    val stock: Int,
    val status: ProductStatusEnum,
    val createDate: LocalDateTime? = null,
    val updateDate: LocalDateTime? = null
)