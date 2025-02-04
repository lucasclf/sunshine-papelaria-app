package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.enums.ProductStatusEnum
import com.sunshine.backend.domain.models.ProductModel
import com.sunshine.backend.domain.repositories.ProductRepository
import com.sunshine.backend.infra.database.tables.Products
import com.sunshine.backend.infra.database.tables.Products.createDate
import com.sunshine.backend.infra.database.tables.Products.updateDate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class ProductRepositoryImpl : ProductRepository {
    override fun getAll(): List<ProductModel> = transaction {
        Products.selectAll().map {
            ProductModel(
                id = it[Products.id],
                name = it[Products.name],
                price = it[Products.price],
                stock = it[Products.stock],
                status = it[Products.status],
                createDate = it[createDate],
                updateDate = it[updateDate]
            )
        }
    }

    override fun getById(productId: Int): ProductModel? = transaction {
        Products.selectAll().where { Products.id eq productId }
            .map {
                ProductModel(
                    id = it[Products.id],
                    name = it[Products.name],
                    price = it[Products.price],
                    stock = it[Products.stock],
                    status = it[Products.status],
                    createDate = it[createDate],
                    updateDate = it[updateDate]
                )
            }
            .singleOrNull()
    }

    override fun insert(productModel: ProductModel): Int = transaction {
        Products.insert {
            it[name] = productModel.name
            it[price] = productModel.price
            it[stock] = productModel.stock
            it[status] = ProductStatusEnum.ACTIVE
        } get Products.id
    }

    override fun update(productModel: ProductModel): Boolean = transaction {
        Products.update({ Products.id eq productModel.id }) {
            it[name] = productModel.name
            it[price] = productModel.price
            it[stock] = productModel.stock
            it[updateDate]= LocalDateTime.now()
        } > 0
    }

    override fun delete(productId: Int): Boolean = transaction {
        Products.deleteWhere { id eq productId } > 0
    }
}