package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.enums.OrderStatus
import com.sunshine.backend.domain.models.Order
import com.sunshine.backend.domain.models.OrderItem
import com.sunshine.backend.domain.repositories.OrderRepository
import com.sunshine.backend.infra.database.tables.OrderItems


import com.sunshine.backend.infra.database.tables.Orders
import com.sunshine.backend.infra.database.tables.Orders.clientId
import com.sunshine.backend.infra.database.tables.Orders.createDate
import com.sunshine.backend.infra.database.tables.Orders.status
import com.sunshine.backend.infra.database.tables.Orders.totalValue
import com.sunshine.backend.infra.database.tables.Orders.updateDate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


class OrderRepositoryImpl : OrderRepository {
    override fun getAll(): List<Order> = transaction {
        val items = OrderItems.selectAll().map {
            OrderItem(
                orderId = it[OrderItems.orderId],
                productId = it[OrderItems.productId],
                quantity = it[OrderItems.quantity],
                createDate = it[OrderItems.createDate],
                updateDate = it[OrderItems.updateDate]
            )
        }

        Orders.selectAll().map {orderRow ->
            Order(
                id = orderRow[Orders.id],
                clientId = orderRow[clientId],
                totalValue = orderRow[totalValue],
                status = orderRow[status],
                items = items.filter { it.orderId == orderRow[Orders.id] },
                createDate = orderRow[createDate],
                updateDate = orderRow[updateDate]
            )
        }
    }

    override fun getById(orderId: Int): Order? = transaction {
        val items = OrderItems.selectAll().where { OrderItems.orderId eq orderId }
            .map {
            OrderItem(
                orderId = it[OrderItems.orderId],
                productId = it[OrderItems.productId],
                quantity = it[OrderItems.quantity],
                createDate = it[OrderItems.createDate],
                updateDate = it[OrderItems.updateDate]
            )
        }

        Orders.selectAll().where { Orders.id eq orderId }
            .map {
                Order(
                    id = it[Orders.id],
                    clientId = it[clientId],
                    totalValue = it[totalValue],
                    status = it[status],
                    items = items,
                    createDate = it[createDate],
                    updateDate = it[updateDate]
                )
            }
    }.singleOrNull()

    override fun insert(order: Order): Int {
        return transaction {
            Orders.insert {
                it[clientId] = order.clientId
                it[totalValue] = order.totalValue
                it[status] = OrderStatus.AWAITING_PAYMENT
            } get Orders.id
        }
    }

    override fun updateStatus(orderId: Int, status: OrderStatus): Boolean = transaction {
        Orders.update({ Orders.id eq orderId }) {
            it[Orders.status] = status
        } > 0
    }

    override fun delete(orderId: Int): Boolean = transaction {
        Orders.update({ Orders.id eq orderId }) {
            it[status] = OrderStatus.CANCELED
        } > 0
    }
}