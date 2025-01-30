package com.sunshine.backend.domain.repositories

import com.sunshine.backend.domain.models.Product

interface ProductRepository {
    fun getAll(): List<Product>
    fun getById(productId: Int): Product?
    fun insert(product: Product): Int
    fun update(product: Product): Boolean
    fun delete(productId: Int): Boolean
}