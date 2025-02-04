package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.models.OrderItemModel
import com.sunshine.backend.domain.repositories.OrderItemRepository
import com.sunshine.backend.infra.database.tables.OrderItems
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class OrderItemRepositoryImpl : OrderItemRepository {
    override fun getAll(): List<OrderItemModel> = transaction {
        OrderItems.selectAll().map {
            OrderItemModel(
                orderId = it[OrderItems.orderId],
                productId = it[OrderItems.productId],
                quantity = it[OrderItems.quantity],
                createDate = it[OrderItems.createDate]
            )
        }
    }

    override fun getById(orderId: Int, productId: Int): OrderItemModel? = transaction {
        OrderItems.selectAll().where {
            (OrderItems.orderId eq orderId) and (OrderItems.productId eq productId)
        }.map{
            OrderItemModel(
                orderId = it[OrderItems.orderId],
                productId = it[OrderItems.productId],
                quantity = it[OrderItems.quantity],
                createDate = it[OrderItems.createDate]
            )
        }
    }.singleOrNull()

    override fun getByOrderId(orderId: Int): List<OrderItemModel> = transaction {
        OrderItems.selectAll().where { OrderItems.orderId eq orderId}.map {
            OrderItemModel(
                orderId = it[OrderItems.orderId],
                productId = it[OrderItems.productId],
                quantity = it[OrderItems.quantity],
                createDate = it[OrderItems.createDate]
            )
        }
    }

    override fun insert(orderId: Int, orderItemModels: List<OrderItemModel>) = transaction {
        OrderItems.batchInsert(orderItemModels) { item ->
            this[OrderItems.orderId] = orderId
            this[OrderItems.productId] = item.productId
            this[OrderItems.quantity] = item.quantity
        }.let {}
    }

    override fun update(orderItemModel: OrderItemModel): Boolean = transaction {
        OrderItems.update({
            (OrderItems.orderId eq orderItemModel.orderId!!) and (OrderItems.productId eq orderItemModel.productId)
        }) {
            it[quantity] = orderItemModel.quantity
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