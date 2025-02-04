package com.sunshine.backend.infra.adapters

import com.sunshine.backend.domain.models.ClientModel
import com.sunshine.backend.infra.database.tables.ClientEntity
import org.jetbrains.exposed.sql.ResultRow


object ClientAdapter {
    fun entityToModel(row: ResultRow): ClientModel {
        return ClientModel(
            id = row[ClientEntity.id],
            name = row[ClientEntity.name],
            address = row[ClientEntity.address],
            cep = row[ClientEntity.cep],
            contact = row[ClientEntity.contact],
            status = row[ClientEntity.status],
            createDate = row[ClientEntity.createDate],
            updateDate = row[ClientEntity.updateDate]
        )
    }
}