package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.enums.ProductStatusEnum
import com.sunshine.backend.domain.models.ProductModel
import com.sunshine.backend.domain.repositories.ProductRepository
import com.sunshine.backend.infra.database.tables.ProductEntity
import com.sunshine.backend.infra.database.tables.ProductEntity.createDate
import com.sunshine.backend.infra.database.tables.ProductEntity.updateDate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class ProductRepositoryImpl : ProductRepository {
    override fun getAll(): List<ProductModel> = transaction {
        ProductEntity.selectAll().map {
            ProductModel(
                id = it[ProductEntity.id],
                name = it[ProductEntity.name],
                price = it[ProductEntity.price],
                stock = it[ProductEntity.stock],
                status = it[ProductEntity.status],
                createDate = it[createDate],
                updateDate = it[updateDate]
            )
        }
    }

    override fun getById(productId: Int): ProductModel? = transaction {
        ProductEntity.selectAll().where { ProductEntity.id eq productId }
            .map {
                ProductModel(
                    id = it[ProductEntity.id],
                    name = it[ProductEntity.name],
                    price = it[ProductEntity.price],
                    stock = it[ProductEntity.stock],
                    status = it[ProductEntity.status],
                    createDate = it[createDate],
                    updateDate = it[updateDate]
                )
            }
            .singleOrNull()
    }

    override fun insert(productModel: ProductModel): Int = transaction {
        ProductEntity.insert {
            it[name] = productModel.name
            it[price] = productModel.price
            it[stock] = productModel.stock
            it[status] = ProductStatusEnum.ACTIVE
        } get ProductEntity.id
    }

    override fun update(productModel: ProductModel): Boolean = transaction {
        ProductEntity.update({ ProductEntity.id eq productModel.id }) {
            it[name] = productModel.name
            it[price] = productModel.price
            it[stock] = productModel.stock
            it[updateDate]= LocalDateTime.now()
        } > 0
    }

    override fun delete(productId: Int): Boolean = transaction {
        ProductEntity.deleteWhere { id eq productId } > 0
    }
}