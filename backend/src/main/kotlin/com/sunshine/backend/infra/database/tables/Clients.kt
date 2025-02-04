package com.sunshine.backend.infra.database.tables

import com.sunshine.backend.domain.enums.ClientStatusEnum
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Clients : Table(){
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val address = varchar("address", 255)
    val cep = varchar("cep", 10)
    val contact = varchar("contact", 30)
    val status = enumerationByName("status", 50, ClientStatusEnum::class)
    val createDate = datetime("create_date").clientDefault { LocalDateTime.now() }
    val updateDate = datetime("update_date").clientDefault { LocalDateTime.now() }

    override val primaryKey = PrimaryKey(id)
}