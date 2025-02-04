package com.sunshine.backend.infra.adapters

import com.sunshine.backend.domain.models.ProductModel
import com.sunshine.backend.infra.database.tables.ProductEntity
import org.jetbrains.exposed.sql.ResultRow

object ProductAdapter {
    fun entityToModel(row: ResultRow): ProductModel {
        return ProductModel(
            id = row[ProductEntity.id],
            name = row[ProductEntity.name],
            price = row[ProductEntity.price],
            stock = row[ProductEntity.stock],
            status = row[ProductEntity.status],
            createDate = row[ProductEntity.createDate],
            updateDate = row[ProductEntity.updateDate]
        )
    }
}