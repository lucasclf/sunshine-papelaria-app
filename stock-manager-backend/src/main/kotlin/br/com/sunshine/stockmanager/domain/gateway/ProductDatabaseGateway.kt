package br.com.sunshine.stockmanager.domain.gateway

import br.com.sunshine.stockmanager.domain.models.ProductModel

interface ProductDatabaseGateway {
    fun saveProduct(product: ProductModel): ProductModel
}