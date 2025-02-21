package br.com.sunshine.stockmanager.api.adapters

import br.com.sunshine.stockmanager.api.requests.products.ProductRequest
import br.com.sunshine.stockmanager.api.responses.ProductResponse
import br.com.sunshine.stockmanager.domain.enums.ProductStatusEnum
import br.com.sunshine.stockmanager.domain.models.ProductModel

object ApiProductAdapter {
    fun requestToModel(request: ProductRequest): ProductModel =
        ProductModel(
            price = request.price,
            stock = request.stock,
            name = request.name,
            status = ProductStatusEnum.ACTIVE
        )

    fun modelToResponse(model: ProductModel): ProductResponse =
        ProductResponse(
            id = model.id!!,
            price = model.price,
            stock = model.stock,
            name = model.name,
            status = model.status,
            createDate = model.createDate.toString(),
            updateDate = model.updateDate.toString()
        )
}