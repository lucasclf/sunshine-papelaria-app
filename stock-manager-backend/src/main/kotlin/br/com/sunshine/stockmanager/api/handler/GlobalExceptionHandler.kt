package br.com.sunshine.stockmanager.api.handler

import br.com.sunshine.stockmanager.api.responses.ErrorResponse
import br.com.sunshine.stockmanager.api.responses.ResponseDataError
import br.com.sunshine.stockmanager.domain.exceptions.SunshineException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(SunshineException::class)
    fun handleSunshineException(ex: SunshineException): ResponseEntity<ResponseDataError> {
        val errorResponse = ResponseDataError(
            ErrorResponse(
                title = ex.error.name,
                code = ex.error.code,
                message = ex.error.description
            )
        )

        return ResponseEntity(errorResponse, ex.error.httpCode)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ResponseDataError> {
        val errorResponse = ResponseDataError(
            ErrorResponse(
                title = "UNKNOWN_ERROR",
                code = "SUN000",
                message = ex.message ?: "An unexpected error occurred"
            )
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}