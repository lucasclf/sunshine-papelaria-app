package br.com.sunshine.stockmanager.domain.enums

import br.com.sunshine.stockmanager.domain.exceptions.SunshineException

enum class ProductStatusEnum() {
    ACTIVE, INACTIVE;

    companion object {
        fun fromValue(value: String?): ProductStatusEnum {
            return ProductStatusEnum.entries.find { it.name == value } ?: throw SunshineException(
                SunshineExceptionEnum.INVALID_PRODUCT_STATUS
            )
        }
    }
}