package br.com.sunshine.stockmanager.domain.usecase

import br.com.sunshine.stockmanager.domain.models.ProductModel

interface FindProductUseCase {
    fun findAll(): List<ProductModel>

    fun findById(id: Int): ProductModel
}