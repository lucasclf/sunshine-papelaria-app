package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.enums.OrderStatusEnum
import com.sunshine.backend.domain.enums.SunshineExceptionEnum
import com.sunshine.backend.domain.exceptions.SunshineException
import com.sunshine.backend.domain.models.OrderModel
import com.sunshine.backend.domain.models.OrderItemModel
import com.sunshine.backend.domain.repositories.OrderRepository
import com.sunshine.backend.infra.database.tables.OrderItemEntity


import com.sunshine.backend.infra.database.tables.OrderEntity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory


class OrderRepositoryImpl : OrderRepository {
    val logger = LoggerFactory.getLogger(this::class.java)

    override fun getAll(): List<OrderModel> = transaction { //TODO USAR ADAPTER
        OrderEntity.selectAll().map { orderRow ->
            OrderModel(
                id = orderRow[OrderEntity.id],
                clientId = orderRow[OrderEntity.clientId],
                totalValue = orderRow[OrderEntity.totalValue],
                status = orderRow[OrderEntity.status],
                items = findItemsByOrderId(orderRow[OrderEntity.id]),
                discount = orderRow[OrderEntity.discount],
                freight = orderRow[OrderEntity.freight],
                carrierName = orderRow[OrderEntity.carrierName],
                trackingCode = orderRow[OrderEntity.trackingCode],
                refundedValue = orderRow[OrderEntity.refundedValue],
                refundedReason = orderRow[OrderEntity.refundedReason],
                refundedDate = orderRow[OrderEntity.refundedDate],
                sentDate = orderRow[OrderEntity.sentDate],
                createDate = orderRow[OrderEntity.createDate],
                updateDate = orderRow[OrderEntity.updateDate]
            )
        }
    }

    override fun getById(orderId: Int): OrderModel = transaction {
        val items = findItemsByOrderId(orderId)

        val order = findOrderId(orderId, items)

        return@transaction order
    }

    override fun insert(orderModel: OrderModel, value: Double): OrderModel = transaction {

        val insertedId = OrderEntity.insert {
            it[clientId] = orderModel.clientId
            it[totalValue] = value
            it[status] = OrderStatusEnum.AWAITING_PAYMENT
        }[OrderEntity.id]

        findOrderId(insertedId)
    }

    override fun updateOrderStatus(orderModel: OrderModel): OrderModel = transaction {
        if(orderModel.status == OrderStatusEnum.PAID) {
            updateOrderToPaid(orderModel)
        }

        if(orderModel.status == OrderStatusEnum.SENT) {
            updateOrderToSent(orderModel)
        }

        if(orderModel.status == OrderStatusEnum.CANCELED){
            updateOrderToCanceled(orderModel.id!!)
        }

        if(orderModel.status == OrderStatusEnum.RECEIVED){
            updatedOrderToReceived(orderModel.id!!)
        }

        if(orderModel.status == OrderStatusEnum.REFUNDED){
            updateOrderToRefunded(orderModel)
        }

        val items = findItemsByOrderId(orderModel.id!!)

        return@transaction findOrderId(orderModel.id, items)
    }

    private fun updateOrderToPaid(orderModel: OrderModel) {
        OrderEntity.update({ OrderEntity.id eq orderModel.id!! }) {
            it[status] = orderModel.status
            it[discount] = orderModel.discount
            it[freight] = orderModel.freight
            it[paymentDate] = orderModel.paymentDate!!
            it[updateDate] = orderModel.paymentDate
        }
    }

    private fun updateOrderToSent(orderModel: OrderModel) {
        OrderEntity.update({ OrderEntity.id eq orderModel.id!! }) {
            it[status] = orderModel.status
            it[carrierName] = orderModel.carrierName!!
            it[trackingCode] = orderModel.trackingCode!!
            it[sentDate] = orderModel.sentDate!!
            it[updateDate] = orderModel.sentDate
        }
    }

    private fun updateOrderToRefunded(orderModel: OrderModel){
        OrderEntity.update({ OrderEntity.id eq orderModel.id!! }) {
            it[status] = orderModel.status
            it[refundedValue] = orderModel.refundedValue
            it[refundedReason] = orderModel.refundedReason!!
            it[refundedDate] = orderModel.refundedDate!!
            it[updateDate] = orderModel.refundedDate
        }
    }

    private fun updateOrderToCanceled(orderId: Int)  {
        OrderEntity.update({ OrderEntity.id eq orderId }) {
            it[status] = OrderStatusEnum.CANCELED
        }
    }

    private fun updatedOrderToReceived(orderId: Int)  {
        OrderEntity.update({ OrderEntity.id eq orderId }) {
            it[status] = OrderStatusEnum.RECEIVED
        }
    }

    private fun findItemsByOrderId(orderId: Int) : List<OrderItemModel>{
        val items = OrderItemEntity.selectAll().where { OrderItemEntity.orderId eq orderId }
            .map {
                OrderItemModel(
                    orderId = it[OrderItemEntity.orderId],
                    productId = it[OrderItemEntity.productId],
                    quantity = it[OrderItemEntity.quantity],
                    createDate = it[OrderItemEntity.createDate]
                )
            }

        if(items.isEmpty()){
            logger.info("Nenhum item encontrado vinculado ao pedido $orderId")
            throw SunshineException(SunshineExceptionEnum.ITEM_NOT_FOUND)
        }

        return items
    }

    private fun findOrderId(orderId: Int, items: List<OrderItemModel> = emptyList()) : OrderModel {
        val order = OrderEntity.selectAll().where { OrderEntity.id eq orderId }
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
                    refundedValue = it[OrderEntity.refundedValue],
                    refundedReason = it[OrderEntity.refundedReason],
                    refundedDate = it[OrderEntity.refundedDate],
                    sentDate = it[OrderEntity.sentDate],
                    createDate = it[OrderEntity.createDate],
                    updateDate = it[OrderEntity.updateDate]
                )
            }.singleOrNull()

        if(order == null){
            logger.info("Pedido $orderId não localizado")
            throw SunshineException(SunshineExceptionEnum.ORDER_NOT_FOUND)
        }

        return order
    }
}