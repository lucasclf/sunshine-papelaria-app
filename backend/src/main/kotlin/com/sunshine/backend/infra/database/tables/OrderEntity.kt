package com.sunshine.backend.infra.database.tables

import com.sunshine.backend.domain.enums.OrderStatusEnum
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object OrderEntity: Table("Orders") {
    val id = integer("id").autoIncrement()
    val clientId = integer("client_id").references(ClientEntity.id, onDelete = ReferenceOption.CASCADE)
    val totalValue = double("total_value")
    val discount = double("discount")
    val freight = double("freight")
    val refundedValue = double("refunded_value")
    val refundedReason = varchar("refunded_reason", 200)
    val status = enumerationByName("status", 50, OrderStatusEnum::class)
    val trackingCode = varchar("tracking_code", 50)
    val carrierName = varchar("carrier_name", 50)
    val refundedDate = datetime("refunded_date")
    val paymentDate = datetime("payment_date")
    val sentDate = datetime("sent_date")
    val createDate = datetime("create_date").clientDefault { LocalDateTime.now() }
    val updateDate = datetime("update_date").clientDefault { LocalDateTime.now() }

    override val primaryKey = PrimaryKey(id)
}