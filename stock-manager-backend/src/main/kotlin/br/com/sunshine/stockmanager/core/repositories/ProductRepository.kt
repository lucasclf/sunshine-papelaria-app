package br.com.sunshine.stockmanager.core.repositories

import br.com.sunshine.stockmanager.core.entities.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<ProductEntity, Int> {
}