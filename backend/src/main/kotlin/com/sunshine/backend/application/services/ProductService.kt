package com.sunshine.backend.application.services

import com.sunshine.backend.application.adapters.ProductAdapter
import com.sunshine.backend.domain.enums.ProductStatusEnum
import com.sunshine.backend.domain.enums.SunshineExceptionEnum
import com.sunshine.backend.domain.exceptions.SunshineException
import com.sunshine.backend.domain.repositories.ProductRepository
import com.sunshine.backend.presentation.requests.ProductRequest
import com.sunshine.backend.presentation.responses.ProductResponse
import org.slf4j.LoggerFactory

class ProductService (private val repository: ProductRepository) {
    val logger = LoggerFactory.getLogger(this::class.java)

    fun getAllProducts(): List<ProductResponse> {
        return repository.getAll().map { productModel ->
            ProductAdapter.modelToResponse(productModel)
        }
    }

    fun getProductById(id: Int): ProductResponse = ProductAdapter.modelToResponse(repository.getById(id))

    fun createProduct(request: ProductRequest): ProductResponse {
        val productModel = repository.insert(ProductAdapter.requestToModel(request))
        return ProductAdapter.modelToResponse(productModel)
    }

    fun updateProduct(request: ProductRequest, productId: Int): ProductResponse {
        val productModel = repository.update(ProductAdapter.requestToModel(request, productId))
        return ProductAdapter.modelToResponse(productModel)
    }

    fun updateProductStatus(id: Int, status: String?): ProductResponse {
        val productStatus = validateStatus(status)
        return ProductAdapter.modelToResponse(repository.changeProductStatus(id, productStatus))
    }

    private fun validateStatus(status: String?): ProductStatusEnum {
        return try {
            ProductStatusEnum.valueOf(status!!)
        } catch (e: Exception) {
            logger.info("O status $status não é válido.")
            throw SunshineException(SunshineExceptionEnum.INVALID_PRODUCT_STATUS)
        }
    }
}