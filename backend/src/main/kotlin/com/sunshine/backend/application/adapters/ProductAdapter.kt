package com.sunshine.backend.application.adapters

import com.sunshine.backend.domain.enums.ProductStatusEnum
import com.sunshine.backend.domain.models.ProductModel
import com.sunshine.backend.presentation.requests.ProductRequest
import com.sunshine.backend.presentation.responses.ProductResponse

object ProductAdapter {
    fun requestToModel(product: ProductRequest, id: Int? = null): ProductModel {
        return ProductModel(
            id = id,
            name = product.name,
            price = product.price,
            stock = product.stock,
            status = ProductStatusEnum.ACTIVE
        )
    }

    fun modelToResponse(model: ProductModel): ProductResponse {
        return ProductResponse(
            id = model.id!!,
            name = model.name,
            price = model.price,
            stock = model.stock,
            status = model.status,
            createDate = model.createDate.toString(),
            updateDate = model.updateDate.toString()
        )
    }
}