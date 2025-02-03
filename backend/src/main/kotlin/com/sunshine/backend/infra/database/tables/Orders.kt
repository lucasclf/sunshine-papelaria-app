package com.sunshine.backend.infra.database.tables

import com.sunshine.backend.domain.enums.OrderStatus
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Orders: Table() {
    val id = integer("id").autoIncrement()
    val clientId = integer("client_id").references(Clients.id, onDelete = ReferenceOption.CASCADE)
    val totalValue = double("total_value")
    val discount = double("discount")
    val freight = double("freight")
    val status = enumerationByName("status", 50, OrderStatus::class)
    val trackingCode = varchar("tracking_code", 50)
    val carrierName = varchar("carrier_name", 50)
    val paymentDate = datetime("payment_date")
    val sentDate = datetime("sent_date")
    val createDate = datetime("create_date").clientDefault { LocalDateTime.now() }
    val updateDate = datetime("update_date").clientDefault { LocalDateTime.now() }

    override val primaryKey = PrimaryKey(id)
}