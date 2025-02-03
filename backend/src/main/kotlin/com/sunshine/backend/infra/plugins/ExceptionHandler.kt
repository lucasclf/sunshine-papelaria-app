package com.sunshine.backend.infra.plugins

import com.sunshine.backend.domain.exceptions.SunshineException
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.slf4j.LoggerFactory

fun Application.configureExceptionHandling() {
    val logger = LoggerFactory.getLogger("ExceptionHandler")

    install(StatusPages) {
        exception<SunshineException> { call, cause ->
            logger.error("Erro capturado: ${cause.error.name} - ${cause.error.description}")

            call.respond(
                cause.error.httpCode,
                mapOf(
                    "code" to cause.error.code,
                    "description" to cause.error.description + cause.errorExtraInfo
                )
            )
        }

        exception<Throwable> { call, cause ->
            logger.error("Erro inesperado: ${cause.message}", cause)
            call.respond(
                io.ktor.http.HttpStatusCode.InternalServerError,
                mapOf(
                    "code" to "SUN500",
                    "description" to cause.message
                )
            )
        }
    }
}