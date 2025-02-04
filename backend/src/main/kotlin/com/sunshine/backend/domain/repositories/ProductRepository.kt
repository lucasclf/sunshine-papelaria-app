package com.sunshine.backend.domain.repositories

import com.sunshine.backend.domain.enums.ProductStatusEnum
import com.sunshine.backend.domain.models.ProductModel

interface ProductRepository {
    fun getAll(): List<ProductModel>
    fun getById(productId: Int): ProductModel
    fun insert(productModel: ProductModel): ProductModel
    fun update(productModel: ProductModel): ProductModel
    fun changeProductStatus(productId: Int, newStatus: ProductStatusEnum): ProductModel
}