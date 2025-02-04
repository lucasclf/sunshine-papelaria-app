package com.sunshine.backend.application.services

import com.sunshine.backend.domain.models.ProductModel
import com.sunshine.backend.domain.repositories.ProductRepository

class ProductService (private val repository: ProductRepository) {
    fun getAllProducts(): List<ProductModel> = repository.getAll()
    fun getProductById(id: Int): ProductModel? = repository.getById(id)
    fun createProduct(productModel: ProductModel): Int = repository.insert(productModel)
    fun updateProduct(productModel: ProductModel): Boolean = repository.update(productModel)
    fun deleteProduct(id: Int): Boolean = repository.delete(id)
}