package com.sunshine.backend.presentation.responses

import com.sunshine.backend.domain.enums.ProductStatusEnum

data class ProductResponse(
    val id: Int,
    val name: String,
    val price: Double,
    val stock: Int,
    val status: ProductStatusEnum,
    val createDate: String,
    val updateDate: String
)