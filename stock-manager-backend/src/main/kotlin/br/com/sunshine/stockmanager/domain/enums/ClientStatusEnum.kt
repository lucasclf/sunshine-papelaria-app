package br.com.sunshine.stockmanager.domain.enums

import br.com.sunshine.stockmanager.domain.exceptions.SunshineException

enum class ClientStatusEnum() {
    ACTIVE, INACTIVE;

    companion object {
        fun fromValue(value: String?): ClientStatusEnum {
            return entries.find { it.name == value } ?: throw SunshineException(
                SunshineExceptionEnum.INVALID_CLIENT_STATUS
            )
        }
    }
}