package com.sunshine.backend.infra.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Products : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val price = double("price")
    val stock = integer("stock")
    val createDate = datetime("create_date").clientDefault { LocalDateTime.now() }
    val updateDate = datetime("update_date").clientDefault { LocalDateTime.now() }

    override val primaryKey = PrimaryKey(id)
}