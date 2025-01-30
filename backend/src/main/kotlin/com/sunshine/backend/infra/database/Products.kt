package com.sunshine.backend.infra.database

import org.jetbrains.exposed.sql.Table

object Products : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val price = double("price")
    val stock = integer("stock")
    override val primaryKey = PrimaryKey(id)
}