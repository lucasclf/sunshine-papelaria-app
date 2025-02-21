package br.com.sunshine.stockmanager.domain.usecase

import br.com.sunshine.stockmanager.domain.models.ProductModel

interface CreateProductUseCase {
    fun create(product: ProductModel): ProductModel
}