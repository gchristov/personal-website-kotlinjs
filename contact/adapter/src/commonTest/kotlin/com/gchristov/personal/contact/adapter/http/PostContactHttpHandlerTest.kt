package com.gchristov.personal.contact.adapter.http

import arrow.core.Either
import com.gchristov.personal.common.kotlin.JsonSerializer
import com.gchristov.personal.common.networktestfixtures.FakeHttpResponse
import com.gchristov.personal.common.test.FakeCoroutineDispatcher
import com.gchristov.personal.common.test.FakeLogger
import com.gchristov.personal.contact.testfixtures.FakeContactHttpRequest
import com.gchristov.personal.contact.testfixtures.FakePostContactUseCase
import io.ktor.http.*
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PostContactHttpHandlerTest {
    @Test
    fun httpConfig(): TestResult = runBlockingTest { handler, _, _ ->
        val config = handler.httpConfig()
        assertEquals(HttpMethod.Post, config.method)
        assertEquals("/api/contact", config.path)
        assertEquals(ContentType.Application.Json, config.contentType)
    }

    @Test
    fun handleRequestSuccessCallsUseCase(): TestResult = runBlockingTest { handler, useCase, _ ->
        handler.handleHttpRequest(FakeContactHttpRequest(), FakeHttpResponse())
        useCase.assertInvokedOnce()
        useCase.assertArgs(
            name = "Alice",
            email = "alice@example.com",
            message = "Hello!",
        )
    }

    @Test
    fun handleRequestSuccessResponds(): TestResult = runBlockingTest { handler, _, response ->
        handler.handleHttpRequest(FakeContactHttpRequest(), response)
        response.assertEquals(
            header = "Content-Type",
            headerValue = ContentType.Application.Json.toString(),
            data = "",
            status = 200,
            filePath = null,
        )
    }

    @Test
    fun handleRequestErrorResponds(): TestResult = runBlockingTest(
        invocationResult = Either.Left(Throwable("Slack unreachable"))
    ) { handler, _, response ->
        handler.handleHttpRequest(FakeContactHttpRequest(), response)
        response.assertEquals(
            header = "Content-Type",
            headerValue = ContentType.Application.Json.toString(),
            data = """{"errorMessage":"Slack unreachable"}""",
            status = 400,
            filePath = null,
        )
    }

    private fun runBlockingTest(
        invocationResult: Either<Throwable, Unit> = Either.Right(Unit),
        testBlock: suspend (PostContactHttpHandler, FakePostContactUseCase, FakeHttpResponse) -> Unit,
    ): TestResult = runTest {
        val useCase = FakePostContactUseCase(invocationResult)
        val response = FakeHttpResponse()
        val handler = PostContactHttpHandler(
            dispatcher = FakeCoroutineDispatcher,
            jsonSerializer = JsonSerializer.Default,
            log = FakeLogger,
            postContactUseCase = useCase,
        )
        testBlock(handler, useCase, response)
    }
}
