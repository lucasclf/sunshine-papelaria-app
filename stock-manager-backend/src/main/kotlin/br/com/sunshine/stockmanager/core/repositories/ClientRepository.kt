package br.com.sunshine.stockmanager.core.repositories

import br.com.sunshine.stockmanager.core.entities.ClientEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository: JpaRepository<ClientEntity, Int>