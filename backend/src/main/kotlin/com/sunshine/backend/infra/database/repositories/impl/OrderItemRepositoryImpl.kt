package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.models.OrderItem
import com.sunshine.backend.domain.repositories.OrderItemRepository
import com.sunshine.backend.infra.database.tables.OrderItems
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class OrderItemRepositoryImpl : OrderItemRepository {
    override fun getAll(): List<OrderItem> = transaction {
        OrderItems.selectAll().map {
            OrderItem(
                orderId = it[OrderItems.orderId],
                productId = it[OrderItems.productId],
                quantity = it[OrderItems.quantity],
                createDate = it[OrderItems.createDate]
            )
        }
    }

    override fun getById(orderId: Int, productId: Int): OrderItem? = transaction {
        OrderItems.selectAll().where {
            (OrderItems.orderId eq orderId) and (OrderItems.productId eq productId)
        }.map{
            OrderItem(
                orderId = it[OrderItems.orderId],
                productId = it[OrderItems.productId],
                quantity = it[OrderItems.quantity],
                createDate = it[OrderItems.createDate]
            )
        }
    }.singleOrNull()

    override fun getByOrderId(orderId: Int): List<OrderItem> = transaction {
        OrderItems.selectAll().where { OrderItems.orderId eq orderId}.map {
            OrderItem(
                orderId = it[OrderItems.orderId],
                productId = it[OrderItems.productId],
                quantity = it[OrderItems.quantity],
                createDate = it[OrderItems.createDate]
            )
        }
    }

    override fun insert(orderId: Int, orderItems: List<OrderItem>) = transaction {
        OrderItems.batchInsert(orderItems) { item ->
            this[OrderItems.orderId] = orderId
            this[OrderItems.productId] = item.productId
            this[OrderItems.quantity] = item.quantity
        }.let {}
    }

    override fun update(orderItem: OrderItem): Boolean = transaction {
        OrderItems.update({
            (OrderItems.orderId eq orderItem.orderId!!) and (OrderItems.productId eq orderItem.productId)
        }) {
            it[quantity] = orderItem.quantity
        } > 0
    }

    override fun deleteItem(orderId: Int, productId: Int): Boolean = transaction {
        OrderItems.deleteWhere {
            (OrderItems.orderId eq orderId) and (OrderItems.productId eq productId)
        } > 0
    }

    override fun deleteOrder(orderId: Int): Boolean = transaction {
        OrderItems.deleteWhere { OrderItems.orderId eq orderId } > 0
    }
}