package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.models.Product
import com.sunshine.backend.domain.repositories.ProductRepository
import com.sunshine.backend.infra.database.Products
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ProductRepositoryImpl : ProductRepository {
    override fun getAll(): List<Product> = transaction {
        Products.selectAll().map {
            Product(
                id = it[Products.id],
                name = it[Products.name],
                price = it[Products.price],
                stock = it[Products.stock]
            )
        }
    }

    override fun getById(productId: Int): Product? = transaction {
        Products.selectAll().where { Products.id eq productId }
            .map {
                Product(
                    id = it[Products.id],
                    name = it[Products.name],
                    price = it[Products.price],
                    stock = it[Products.stock]
                )
            }
            .singleOrNull()
    }

    override fun insert(product: Product): Int = transaction {
        Products.insert {
            it[name] = product.name
            it[price] = product.price
            it[stock] = product.stock
        } get Products.id
    }

    override fun update(product: Product): Boolean = transaction {
        Products.update({ Products.id eq product.id }) {
            it[name] = product.name
            it[price] = product.price
            it[stock] = product.stock
        } > 0
    }

    override fun delete(productId: Int): Boolean = transaction {
        Products.deleteWhere { id eq productId } > 0
    }
}