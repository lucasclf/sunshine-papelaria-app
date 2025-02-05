package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.models.OrderItemModel
import com.sunshine.backend.domain.repositories.OrderItemRepository
import com.sunshine.backend.infra.database.tables.OrderItemEntity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class OrderItemRepositoryImpl : OrderItemRepository {
    override fun getAll(): List<OrderItemModel> = transaction {
        OrderItemEntity.selectAll().map {
            OrderItemModel(
                orderId = it[OrderItemEntity.orderId],
                productId = it[OrderItemEntity.productId],
                quantity = it[OrderItemEntity.quantity],
                createDate = it[OrderItemEntity.createDate]
            )
        }
    }

    override fun getById(orderId: Int, productId: Int): OrderItemModel? = transaction {
        OrderItemEntity.selectAll().where {
            (OrderItemEntity.orderId eq orderId) and (OrderItemEntity.productId eq productId)
        }.map{
            OrderItemModel(
                orderId = it[OrderItemEntity.orderId],
                productId = it[OrderItemEntity.productId],
                quantity = it[OrderItemEntity.quantity],
                createDate = it[OrderItemEntity.createDate]
            )
        }
    }.singleOrNull()

    override fun getByOrderId(orderId: Int): List<OrderItemModel> = transaction {
        OrderItemEntity.selectAll().where { OrderItemEntity.orderId eq orderId}.map {
            OrderItemModel(
                orderId = it[OrderItemEntity.orderId],
                productId = it[OrderItemEntity.productId],
                quantity = it[OrderItemEntity.quantity],
                createDate = it[OrderItemEntity.createDate]
            )
        }
    }

    override fun insert(orderId: Int, orderItemModels: List<OrderItemModel>): List<OrderItemModel> = transaction {
        OrderItemEntity.batchInsert(orderItemModels) { item ->
            this[OrderItemEntity.orderId] = orderId
            this[OrderItemEntity.productId] = item.productId
            this[OrderItemEntity.quantity] = item.quantity
        }.map {
            OrderItemModel(
                orderId = it[OrderItemEntity.orderId],
                productId = it[OrderItemEntity.productId],
                quantity = it[OrderItemEntity.quantity],
                createDate = it[OrderItemEntity.createDate]
            )
        }
    }

    override fun update(orderItemModel: OrderItemModel): Boolean = transaction {
        OrderItemEntity.update({
            (OrderItemEntity.orderId eq orderItemModel.orderId!!) and (OrderItemEntity.productId eq orderItemModel.productId)
        }) {
            it[quantity] = orderItemModel.quantity
        } > 0
    }

    override fun deleteItem(orderId: Int, productId: Int): Boolean = transaction {
        OrderItemEntity.deleteWhere {
            (OrderItemEntity.orderId eq orderId) and (OrderItemEntity.productId eq productId)
        } > 0
    }

    override fun deleteOrder(orderId: Int): Boolean = transaction {
        OrderItemEntity.deleteWhere { OrderItemEntity.orderId eq orderId } > 0
    }
}