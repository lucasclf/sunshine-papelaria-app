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
    val status = enumerationByName("status", 50, OrderStatus::class)
    val createDate = datetime("create_date").clientDefault { LocalDateTime.now() }
    val updateDate = datetime("update_date").clientDefault { LocalDateTime.now() }

    override val primaryKey = PrimaryKey(id)
}