package com.sunshine.backend.infra.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object OrderItemEntity: Table("OrderItems") {
    val orderId = integer("order_id").references(OrderEntity.id, onDelete = ReferenceOption.CASCADE)
    val productId = integer("product_id").references(ProductEntity.id, onDelete = ReferenceOption.RESTRICT)
    val quantity = integer("quantity")
    val createDate = datetime("create_date").clientDefault { LocalDateTime.now() }

    override val primaryKey = PrimaryKey(orderId, productId)
}