package com.sunshine.backend.infra.database.repositories.impl

import com.sunshine.backend.domain.enums.ProductStatusEnum
import com.sunshine.backend.domain.enums.SunshineExceptionEnum
import com.sunshine.backend.domain.exceptions.SunshineException
import com.sunshine.backend.domain.models.ProductModel
import com.sunshine.backend.domain.repositories.ProductRepository
import com.sunshine.backend.infra.adapters.ProductAdapter
import com.sunshine.backend.infra.database.tables.ProductEntity
import com.sunshine.backend.infra.database.tables.ProductEntity.createDate
import com.sunshine.backend.infra.database.tables.ProductEntity.updateDate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

class ProductRepositoryImpl : ProductRepository {
    val logger = LoggerFactory.getLogger(this::class.java)

    override fun getAll(): List<ProductModel> = transaction {
        ProductEntity.selectAll().map {
            ProductModel(
                id = it[ProductEntity.id],
                name = it[ProductEntity.name],
                price = it[ProductEntity.price],
                stock = it[ProductEntity.stock],
                status = it[ProductEntity.status],
                createDate = it[createDate],
                updateDate = it[updateDate]
            )
        }
    }

    override fun getById(productId: Int): ProductModel {
        return findProductById(productId) ?: throw SunshineException(
            SunshineExceptionEnum.PRODUCT_NOT_FOUND,
            " ID: $productId"
        ).also { logger.info("Produto de id $productId não localizado.") }
    }

    override fun insert(productModel: ProductModel): ProductModel = transaction {
        val insertedId = ProductEntity.insert {
            it[name] = productModel.name
            it[price] = productModel.price
            it[stock] = productModel.stock
            it[status] = ProductStatusEnum.ACTIVE
        } get ProductEntity.id

        findProductById(insertedId) ?: throw SunshineException(SunshineExceptionEnum.DATABASE_FAIL)
    }

    override fun update(productModel: ProductModel): ProductModel = transaction {
        val updatedRows = ProductEntity.update({ ProductEntity.id eq productModel.id!! }) {
            it[name] = productModel.name
            it[price] = productModel.price
            it[stock] = productModel.stock
            it[updateDate]= LocalDateTime.now()
        }

        findProductById(productModel.id!!).also {
            if (updatedRows == 0) logger.info("Produto de id ${productModel.id} não encontrado após a atualização.")
        } ?: throw SunshineException(SunshineExceptionEnum.PRODUCT_NOT_FOUND, " ID: ${productModel.id}")
    }

    override fun changeProductStatus(productId: Int, newStatus: ProductStatusEnum): ProductModel = transaction {
        val updatedRows = ProductEntity.update({ ProductEntity.id eq productId }) {
            it[status] = newStatus
        }

        findProductById(productId).also {
            if (updatedRows == 0) logger.info("Produto de id $productId não encontrado após a atualização.")
        } ?: throw SunshineException(SunshineExceptionEnum.PRODUCT_NOT_FOUND, " ID: $productId")
    }

    private fun findProductById(productID: Int): ProductModel? = transaction {
        ProductEntity.selectAll().where { ProductEntity.id eq productID }
            .map(ProductAdapter::entityToModel)
            .firstOrNull()
    }
}