package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.enums.OrderStatus
import com.sunshine.backend.domain.models.Order
import com.sunshine.backend.domain.repositories.OrderRepository


import com.sunshine.backend.infra.database.tables.Orders
import com.sunshine.backend.infra.database.tables.Orders.clientId
import com.sunshine.backend.infra.database.tables.Orders.createDate
import com.sunshine.backend.infra.database.tables.Orders.status
import com.sunshine.backend.infra.database.tables.Orders.totalValue
import com.sunshine.backend.infra.database.tables.Orders.updateDate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime


class OrderRepositoryImpl : OrderRepository {
    override fun getAll(): List<Order> = transaction {
        Orders.selectAll().map {
            Order(
                id = it[Orders.id],
                clientId = it[clientId],
                totalValue = it[totalValue],
                status = it[status],
                createDate = it[createDate],
                updateDate = it[updateDate]
            )
        }
    }

    override fun getById(orderId: Int): Order? = transaction {
        Orders.selectAll().where { Orders.id eq orderId }
            .map {
                Order(
                    id = it[Orders.id],
                    clientId = it[clientId],
                    totalValue = it[totalValue],
                    status = it[status],
                    createDate = it[createDate],
                    updateDate = it[updateDate]
                )
            }
    }.singleOrNull()

    override fun insert(order: Order): Int {
        return transaction {
            Orders.insert {
                it[clientId] = clientId
                it[totalValue] = totalValue
                it[status] = OrderStatus.AWAITING_PAYMENT
            } get Orders.id
        }
    }

    override fun update(order: Order): Boolean = transaction {
        Orders.update({ Orders.id eq order.id}) {
            it[clientId] = order.clientId
            it[totalValue] = order.totalValue
            it[status] = order.status
            it[updateDate]= LocalDateTime.now()
        } > 0
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