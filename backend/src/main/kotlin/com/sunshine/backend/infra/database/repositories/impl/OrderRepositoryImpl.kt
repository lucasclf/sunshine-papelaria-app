package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.enums.OrderStatusEnum
import com.sunshine.backend.domain.models.OrderModel
import com.sunshine.backend.domain.models.OrderItemModel
import com.sunshine.backend.domain.models.OrderPaidUpdateModel
import com.sunshine.backend.domain.models.OrderSentUpdateModel
import com.sunshine.backend.domain.repositories.OrderRepository
import com.sunshine.backend.infra.database.tables.OrderItemEntity


import com.sunshine.backend.infra.database.tables.OrderEntity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


class OrderRepositoryImpl : OrderRepository {
    override fun getAll(): List<OrderModel> = transaction {
        val items = OrderItemEntity.selectAll().map {
            OrderItemModel(
                orderId = it[OrderItemEntity.orderId],
                productId = it[OrderItemEntity.productId],
                quantity = it[OrderItemEntity.quantity],
                createDate = it[OrderItemEntity.createDate]
            )
        }

        OrderEntity.selectAll().map { orderRow ->
            OrderModel(
                id = orderRow[OrderEntity.id],
                clientId = orderRow[OrderEntity.clientId],
                totalValue = orderRow[OrderEntity.totalValue],
                status = orderRow[OrderEntity.status],
                items = items.filter { it.orderId == orderRow[OrderEntity.id] },
                discount = orderRow[OrderEntity.discount],
                freight = orderRow[OrderEntity.freight],
                carrierName = orderRow[OrderEntity.carrierName],
                trackingCode = orderRow[OrderEntity.trackingCode],
                sentDate = orderRow[OrderEntity.sentDate],
                createDate = orderRow[OrderEntity.createDate],
                updateDate = orderRow[OrderEntity.updateDate]
            )
        }
    }

    override fun getById(orderId: Int): OrderModel? = transaction {
        val items = OrderItemEntity.selectAll().where { OrderItemEntity.orderId eq orderId }
            .map {
            OrderItemModel(
                orderId = it[OrderItemEntity.orderId],
                productId = it[OrderItemEntity.productId],
                quantity = it[OrderItemEntity.quantity],
                createDate = it[OrderItemEntity.createDate]
            )
        }

        OrderEntity.selectAll().where { OrderEntity.id eq orderId }
            .map {
                OrderModel(
                    id = it[OrderEntity.id],
                    clientId = it[OrderEntity.clientId],
                    totalValue = it[OrderEntity.totalValue],
                    status = it[OrderEntity.status],
                    items = items,
                    discount = it[OrderEntity.discount],
                    freight = it[OrderEntity.freight],
                    carrierName = it[OrderEntity.carrierName],
                    trackingCode = it[OrderEntity.trackingCode],
                    sentDate = it[OrderEntity.sentDate],
                    createDate = it[OrderEntity.createDate],
                    updateDate = it[OrderEntity.updateDate]
                )
            }
    }.singleOrNull()

    override fun insert(orderModel: OrderModel, value: Double): Int {
        return transaction {
            OrderEntity.insert {
                it[clientId] = orderModel.clientId
                it[totalValue] = value
                it[status] = OrderStatusEnum.AWAITING_PAYMENT
            } get OrderEntity.id
        }
    }

    override fun updateOrderToPaid(orderId: Int, update: OrderPaidUpdateModel): Boolean = transaction {
        OrderEntity.update({ OrderEntity.id eq orderId }) {
            it[status] = OrderStatusEnum.PAID
            it[discount] = update.discount
            it[freight] = update.freight
            it[paymentDate] = update.paymentDate
            it[updateDate] = update.paymentDate
        } > 0
    }

    override fun updateOrderToSent(orderId: Int, update: OrderSentUpdateModel): Boolean = transaction {
        OrderEntity.update({ OrderEntity.id eq orderId }) {
            it[status] = OrderStatusEnum.SENT
            it[carrierName] = update.carrierName
            it[trackingCode] = update.trackingCode
            it[sentDate] = update.sentDate
            it[updateDate] = update.sentDate
        } > 0
    }

    override fun delete(orderId: Int): Boolean = transaction {
        OrderEntity.update({ OrderEntity.id eq orderId }) {
            it[status] = OrderStatusEnum.CANCELED
        } > 0
    }
}