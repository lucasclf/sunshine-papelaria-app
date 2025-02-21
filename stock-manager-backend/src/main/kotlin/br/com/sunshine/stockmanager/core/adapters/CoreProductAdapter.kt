package br.com.sunshine.stockmanager.core.adapters

import br.com.sunshine.stockmanager.core.entities.ProductEntity
import br.com.sunshine.stockmanager.domain.models.ProductModel

object CoreProductAdapter {
    fun entityToModel(entity: ProductEntity): ProductModel =
        ProductModel(
            id = entity.id,
            name = entity.name!!,
            price = entity.price!!,
            stock = entity.stock!!,
            status = entity.status!!,
            createDate = entity.createDate,
            updateDate = entity.updateDate
        )

    fun modelToEntity(model: ProductModel): ProductEntity =
        ProductEntity(
            id = model.id,
            name = model.name,
            price = model.price,
            stock = model.stock,
            status = model.status
        )
}