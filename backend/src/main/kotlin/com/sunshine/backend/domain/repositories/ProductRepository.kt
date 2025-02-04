package com.sunshine.backend.domain.repositories

import com.sunshine.backend.domain.models.ProductModel

interface ProductRepository {
    fun getAll(): List<ProductModel>
    fun getById(productId: Int): ProductModel?
    fun insert(productModel: ProductModel): Int
    fun update(productModel: ProductModel): Boolean
    fun delete(productId: Int): Boolean
}