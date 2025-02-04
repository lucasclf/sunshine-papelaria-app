package com.sunshine.backend.infra.database.tables

import com.sunshine.backend.domain.enums.ProductStatusEnum
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object ProductEntity : Table("Products") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val price = double("price")
    val stock = integer("stock")
    val status = enumerationByName("status", 50, ProductStatusEnum::class)
    val createDate = datetime("create_date").clientDefault { LocalDateTime.now() }
    val updateDate = datetime("update_date").clientDefault { LocalDateTime.now() }

    override val primaryKey = PrimaryKey(id)
}