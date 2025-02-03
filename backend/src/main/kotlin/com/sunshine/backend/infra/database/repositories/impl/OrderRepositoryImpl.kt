package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.enums.OrderStatus
import com.sunshine.backend.domain.models.Order
import com.sunshine.backend.domain.models.OrderItem
import com.sunshine.backend.domain.models.OrderPaidUpdate
import com.sunshine.backend.domain.models.OrderSentUpdate
import com.sunshine.backend.domain.repositories.OrderRepository
import com.sunshine.backend.infra.database.tables.OrderItems


import com.sunshine.backend.infra.database.tables.Orders
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


class OrderRepositoryImpl : OrderRepository {
    override fun getAll(): List<Order> = transaction {
        val items = OrderItems.selectAll().map {
            OrderItem(
                orderId = it[OrderItems.orderId],
                productId = it[OrderItems.productId],
                quantity = it[OrderItems.quantity],
                createDate = it[OrderItems.createDate]
            )
        }

        Orders.selectAll().map {orderRow ->
            Order(
                id = orderRow[Orders.id],
                clientId = orderRow[Orders.clientId],
                totalValue = orderRow[Orders.totalValue],
                status = orderRow[Orders.status],
                items = items.filter { it.orderId == orderRow[Orders.id] },
                discount = orderRow[Orders.discount],
                freight = orderRow[Orders.freight],
                carrierName = orderRow[Orders.carrierName],
                trackingCode = orderRow[Orders.trackingCode],
                sentDate = orderRow[Orders.sentDate],
                createDate = orderRow[Orders.createDate],
                updateDate = orderRow[Orders.updateDate]
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
                createDate = it[OrderItems.createDate]
            )
        }

        Orders.selectAll().where { Orders.id eq orderId }
            .map {
                Order(
                    id = it[Orders.id],
                    clientId = it[Orders.clientId],
                    totalValue = it[Orders.totalValue],
                    status = it[Orders.status],
                    items = items,
                    discount = it[Orders.discount],
                    freight = it[Orders.freight],
                    carrierName = it[Orders.carrierName],
                    trackingCode = it[Orders.trackingCode],
                    sentDate = it[Orders.sentDate],
                    createDate = it[Orders.createDate],
                    updateDate = it[Orders.updateDate]
                )
            }
    }.singleOrNull()

    override fun insert(order: Order, value: Double): Int {
        return transaction {
            Orders.insert {
                it[clientId] = order.clientId
                it[totalValue] = value
                it[status] = OrderStatus.AWAITING_PAYMENT
            } get Orders.id
        }
    }

    override fun updateOrderToPaid(orderId: Int, update: OrderPaidUpdate): Boolean = transaction {
        Orders.update({ Orders.id eq orderId }) {
            it[status] = OrderStatus.PAID
            it[discount] = update.discount
            it[freight] = update.freight
            it[paymentDate] = update.paymentDate
            it[updateDate] = update.paymentDate
        } > 0
    }

    override fun updateOrderToSent(orderId: Int, update: OrderSentUpdate): Boolean = transaction {
        Orders.update({ Orders.id eq orderId }) {
            it[status] = OrderStatus.SENT
            it[carrierName] = update.carrierName
            it[trackingCode] = update.trackingCode
            it[sentDate] = update.sentDate
            it[updateDate] = update.sentDate
        } > 0
    }

    override fun delete(orderId: Int): Boolean = transaction {
        Orders.update({ Orders.id eq orderId }) {
            it[status] = OrderStatus.CANCELED
        } > 0
    }
}