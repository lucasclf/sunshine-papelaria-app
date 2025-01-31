package com.sunshine.backend.infra.database

import com.sunshine.backend.infra.database.tables.Clients
import com.sunshine.backend.infra.database.tables.Products
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val database = Database.connect("jdbc:sqlite:database.db", driver = "org.sqlite.JDBC")

        transaction(database) {
            SchemaUtils.create(Products)
            SchemaUtils.create(Clients)
        }
    }
}