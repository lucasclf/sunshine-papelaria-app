package com.sunshine.backend.database

import com.sunshine.backend.models.Product
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object ProductDAO {
    fun getAll(): List<Product> = transaction {
        Products.selectAll().map {
            Product(
                id = it[Products.id],
                name = it[Products.name],
                price = it[Products.price],
                stock = it[Products.stock]
            )
        }
    }

    fun getById(productId: Int): Product? = transaction {
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

    fun insert(product: Product): Int = transaction {
        Products.insert {
            it[name] = product.name
            it[price] = product.price
            it[stock] = product.stock
        } get Products.id
    }

    fun update(product: Product): Boolean = transaction {
        Products.update({ Products.id eq product.id }) {
            it[name] = product.name
            it[price] = product.price
            it[stock] = product.stock
        } > 0
    }

    fun delete(productId: Int): Boolean = transaction {
        Products.deleteWhere { Products.id eq productId } > 0
    }
}