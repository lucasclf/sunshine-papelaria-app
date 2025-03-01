package br.com.sunshine.stockmanager.domain.usecase.impl

import br.com.sunshine.stockmanager.domain.enums.SunshineExceptionEnum
import br.com.sunshine.stockmanager.domain.exceptions.SunshineException
import br.com.sunshine.stockmanager.domain.gateway.ProductDatabaseGateway
import br.com.sunshine.stockmanager.domain.models.ProductModel
import br.com.sunshine.stockmanager.domain.usecase.FindProductUseCase
import org.springframework.stereotype.Component

@Component
class FindProductUseCaseImpl(
    private val productGateway: ProductDatabaseGateway,
): FindProductUseCase {
    override fun findAll(): List<ProductModel> {
        return productGateway.findAllProducts()
    }

    override fun findById(id: Int): ProductModel {
        val product = productGateway.findById(id)

        return product ?: throw SunshineException(SunshineExceptionEnum.PRODUCT_NOT_FOUND)
    }
}