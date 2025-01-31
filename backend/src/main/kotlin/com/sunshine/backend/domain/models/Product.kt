package com.sunshine.backend.domain.models

import com.sunshine.backend.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val stock: Int,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createDate:LocalDateTime? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updateDate: LocalDateTime? = null
)