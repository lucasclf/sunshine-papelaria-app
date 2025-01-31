package com.sunshine.backend.infra.database

import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val database = Database.connect("jdbc:sqlite:database.db", driver = "org.sqlite.JDBC")

        Flyway.configure()
            .dataSource("jdbc:sqlite:database.db", null, null)
            .load()
            .migrate()

        transaction(database) {

        }
    }
}