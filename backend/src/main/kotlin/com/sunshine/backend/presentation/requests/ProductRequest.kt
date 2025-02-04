package com.sunshine.backend.presentation.requests

data class ProductRequest (
    val name: String,
    val price: Double,
    val stock: Int
)