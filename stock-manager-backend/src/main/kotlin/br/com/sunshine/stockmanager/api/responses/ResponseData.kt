package br.com.sunshine.stockmanager.api.responses

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.validation.Valid

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseData<T>(
    @field: Valid
    val data: T
) {
    companion object {
        fun <T> of(
            data: T
        ): ResponseData<T> {
            return ResponseData(data)
        }
    }
}
