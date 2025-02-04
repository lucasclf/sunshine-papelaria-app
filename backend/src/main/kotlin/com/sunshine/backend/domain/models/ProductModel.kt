package com.sunshine.backend.domain.models

import com.sunshine.backend.domain.enums.ProductStatusEnum
import com.sunshine.backend.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ProductModel(
    val id: Int,
    val name: String,
    val price: Double,
    val stock: Int,
    val status: ProductStatusEnum,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createDate:LocalDateTime? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updateDate: LocalDateTime? = null
)