package com.sunshine.backend.infra.adapters

import com.sunshine.backend.domain.models.OrderItemModel
import com.sunshine.backend.domain.models.OrderModel
import com.sunshine.backend.infra.database.tables.OrderEntity
import org.jetbrains.exposed.sql.ResultRow


object OrderAdapter {
    fun entityToModel(orderEntity: ResultRow, itemsEntity: ResultRow):  OrderModel {
        return OrderModel(
            id = orderEntity[OrderEntity.id],
            clientId =  orderEntity[OrderEntity.clientId],
            totalValue = orderEntity[OrderEntity.totalValue],
            status = orderEntity[OrderEntity.status],
            items = itemEntityToItemModel(itemsEntity),
            freight = orderEntity[OrderEntity.freight],
            discount = orderEntity[OrderEntity.discount],
            trackingCode = orderEntity[OrderEntity.trackingCode],
            carrierName = orderEntity[OrderEntity.carrierName],
            paymentDate = orderEntity[OrderEntity.paymentDate],
            sentDate = orderEntity[OrderEntity.sentDate],
            createDate = orderEntity[OrderEntity.createDate],
            updateDate = orderEntity[OrderEntity.updateDate],
        )
    }

    private fun itemEntityToItemModel(itemsEntity: ResultRow): List<OrderItemModel> {
        TODO()
    }
}