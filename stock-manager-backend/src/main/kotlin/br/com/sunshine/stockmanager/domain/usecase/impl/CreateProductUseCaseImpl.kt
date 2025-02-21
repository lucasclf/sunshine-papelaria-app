package br.com.sunshine.stockmanager.domain.usecase.impl

import br.com.sunshine.stockmanager.domain.gateway.ProductDatabaseGateway
import br.com.sunshine.stockmanager.domain.models.ProductModel
import br.com.sunshine.stockmanager.domain.usecase.CreateProductUseCase
import org.springframework.stereotype.Component

@Component
class CreateProductUseCaseImpl(
    private val productDatabaseGateway: ProductDatabaseGateway
): CreateProductUseCase {
    override fun create(product: ProductModel): ProductModel {
        return productDatabaseGateway.saveProduct(product)
    }
}