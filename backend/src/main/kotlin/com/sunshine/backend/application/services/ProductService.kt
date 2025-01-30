package com.sunshine.backend.application.services

import com.sunshine.backend.domain.models.Product
import com.sunshine.backend.domain.repositories.ProductRepository

class ProductService (private val repository: ProductRepository) {
    fun getAllProducts(): List<Product> = repository.getAll()
    fun getProductById(id: Int): Product? = repository.getById(id)
    fun createProduct(product: Product): Int = repository.insert(product)
    fun updateProduct(product: Product): Boolean = repository.update(product)
    fun deleteProduct(id: Int): Boolean = repository.delete(id)
}