package br.com.sunshine.stockmanager.api.requests.products

import jakarta.validation.constraints.NotBlank

data class ProductRequest(
    @field:NotBlank val name: String,
    @field:NotBlank val price: Double,
    @field:NotBlank val stock: Int
)
