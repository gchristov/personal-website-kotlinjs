package com.gchristov.personal.contact.adapter.http

import arrow.core.Either
import arrow.core.flatMap
import co.touchlab.kermit.Logger
import com.gchristov.personal.common.kotlin.JsonSerializer
import com.gchristov.personal.common.network.http.*
import com.gchristov.personal.contact.adapter.http.model.ApiContact
import com.gchristov.personal.contact.domain.usecase.PostContactUseCase
import io.ktor.http.*
import kotlinx.coroutines.CoroutineDispatcher

class PostContactHttpHandler(
    dispatcher: CoroutineDispatcher,
    private val jsonSerializer: JsonSerializer,
    log: Logger,
    private val postContactUseCase: PostContactUseCase,
) : BaseHttpHandler(
    dispatcher = dispatcher,
    jsonSerializer = jsonSerializer,
    log = log,
) {
    override fun httpConfig() = HttpHandler.HttpConfig(
        method = HttpMethod.Post,
        path = "/api/contact",
        contentType = ContentType.Application.Json,
    )

    override suspend fun handleHttpRequestAsync(
        request: HttpRequest,
        response: HttpResponse,
    ): Either<Throwable, Unit> = request
        .decodeBodyFromJson(
            jsonSerializer = jsonSerializer,
            strategy = ApiContact.serializer(),
        )
        .flatMap { body ->
            val contact = requireNotNull(body) { "Request body is required" }
            require(contact.name.isNotBlank() && contact.email.isNotBlank() && contact.message.isNotBlank()) {
                "All fields are required"
            }
            postContactUseCase(
                name = contact.name,
                email = contact.email,
                message = contact.message,
            )
        }
        .flatMap {
            response.sendEmpty()
        }
}
