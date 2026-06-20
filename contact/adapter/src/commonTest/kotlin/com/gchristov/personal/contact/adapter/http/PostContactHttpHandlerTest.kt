package com.gchristov.personal.contact.adapter.http

import arrow.core.Either
import com.gchristov.personal.common.kotlin.JsonSerializer
import com.gchristov.personal.common.network.http.HttpRequest
import com.gchristov.personal.common.network.http.HttpResponse
import com.gchristov.personal.common.kotlin.ParameterMap
import com.gchristov.personal.common.test.FakeCoroutineDispatcher
import com.gchristov.personal.common.test.FakeLogger
import com.gchristov.personal.contact.domain.usecase.PostContactUseCase
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.DeserializationStrategy
import kotlin.test.Test
import kotlin.test.assertEquals

class PostContactHttpHandlerTest {
    @Test
    fun validBodyCallsUseCaseWithCorrectArgs() = runTest {
        val useCase = FakePostContactUseCase()
        val handler = PostContactHttpHandler(
            dispatcher = FakeCoroutineDispatcher,
            jsonSerializer = JsonSerializer.Default,
            log = FakeLogger,
            postContactUseCase = useCase,
        )
        val request = FakeHttpRequest(
            body = """{"name":"Alice","email":"alice@example.com","message":"Hello!"}"""
        )
        val response = FakeHttpResponse()

        handler.handleHttpRequestAsync(request, response)

        assertEquals(expected = "Alice", actual = useCase.capturedName)
        assertEquals(expected = "alice@example.com", actual = useCase.capturedEmail)
        assertEquals(expected = "Hello!", actual = useCase.capturedMessage)
        assertEquals(expected = 200, actual = response.lastStatus)
    }

    @Test
    fun useCaseErrorReturnsErrorResponse() = runTest {
        val useCase = FakePostContactUseCase(response = Either.Left(Throwable("Slack unreachable")))
        val handler = PostContactHttpHandler(
            dispatcher = FakeCoroutineDispatcher,
            jsonSerializer = JsonSerializer.Default,
            log = FakeLogger,
            postContactUseCase = useCase,
        )
        val request = FakeHttpRequest(
            body = """{"name":"Alice","email":"alice@example.com","message":"Hello!"}"""
        )
        val response = FakeHttpResponse()

        handler.handleHttpRequestAsync(request, response)

        assertEquals(expected = 400, actual = response.lastStatus)
    }
}

private class FakePostContactUseCase(
    val response: Either<Throwable, Unit> = Either.Right(Unit),
) : PostContactUseCase {
    var capturedName: String? = null
    var capturedEmail: String? = null
    var capturedMessage: String? = null

    override suspend fun invoke(
        name: String,
        email: String,
        message: String,
    ): Either<Throwable, Unit> {
        capturedName = name
        capturedEmail = email
        capturedMessage = message
        return response
    }
}

private class FakeHttpRequest(private val body: String) : HttpRequest {
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
    override val bodyString = body
    override val body: Any = body

    override fun <T> decodeBodyFromJson(
        jsonSerializer: JsonSerializer,
        strategy: DeserializationStrategy<T>,
    ): Either<Throwable, T?> = try {
        Either.Right(jsonSerializer.json.decodeFromString(strategy, body))
    } catch (error: Throwable) {
        Either.Left(error)
    }
}

private class FakeHttpResponse : HttpResponse {
    var lastStatus: Int? = null
    private var lastData: String? = null
    private var lastHeader: String? = null
    private var lastHeaderValue: String? = null

    override fun send(string: String) { lastData = string }
    override fun sendFile(localPath: String) {}
    override fun setHeader(header: String, value: String) {
        lastHeader = header
        lastHeaderValue = value
    }
    override fun redirect(path: String) {}
    override fun status(status: Int) { lastStatus = status }
}
