package com.gchristov.personal.contact.testfixtures

import arrow.core.Either
import com.gchristov.personal.common.kotlin.JsonSerializer
import com.gchristov.personal.common.kotlin.ParameterMap
import com.gchristov.personal.common.network.http.HttpRequest
import kotlinx.serialization.DeserializationStrategy

class FakeContactHttpRequest(
    private val fakeName: String = "Alice",
    private val fakeEmail: String = "alice@example.com",
    private val fakeMessage: String = "Hello!",
) : HttpRequest {
    override val baseURL = ""
    override val hostname = ""
    override val ip = ""
    override val ips: Array<String>? = null
    override val method = "POST"
    override val path = "/api/contact"
    override val protocol = "http"
    override val headers = object : ParameterMap {
        override fun <T> get(key: String): T? = null
    }
    override val query = object : ParameterMap {
        override fun <T> get(key: String): T? = null
    }
    override val body: Any? = null
    override val bodyString: String? = null

    override fun <T> decodeBodyFromJson(
        jsonSerializer: JsonSerializer,
        strategy: DeserializationStrategy<T>,
    ): Either<Throwable, T?> = try {
        val rawBody = """{"name":"$fakeName","email":"$fakeEmail","message":"$fakeMessage"}"""
        Either.Right(jsonSerializer.json.decodeFromString(strategy, rawBody))
    } catch (error: Throwable) {
        Either.Left(error)
    }
}
