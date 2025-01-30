package com.sunshine.backend.infra.database

import org.jetbrains.exposed.sql.Table

object Clients : Table(){
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val address = varchar("address", 255)
    val cep = varchar("cep", 10)
    val contact = varchar("contact", 30)
    override val primaryKey = PrimaryKey(id)
}