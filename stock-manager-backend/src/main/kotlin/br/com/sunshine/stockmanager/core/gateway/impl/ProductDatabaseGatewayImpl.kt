package br.com.sunshine.stockmanager.core.gateway.impl

import br.com.sunshine.stockmanager.core.adapters.CoreProductAdapter
import br.com.sunshine.stockmanager.core.repositories.ProductRepository
import br.com.sunshine.stockmanager.domain.gateway.ProductDatabaseGateway
import br.com.sunshine.stockmanager.domain.models.ProductModel
import org.springframework.stereotype.Component

@Component
class ProductDatabaseGatewayImpl(
    private val repository: ProductRepository
): ProductDatabaseGateway {
    override fun saveProduct(product: ProductModel): ProductModel {
        val entity = CoreProductAdapter.modelToEntity(product)
        val savedEntity = repository.saveAndFlush(entity)

        return CoreProductAdapter.entityToModel(savedEntity)
    }
}