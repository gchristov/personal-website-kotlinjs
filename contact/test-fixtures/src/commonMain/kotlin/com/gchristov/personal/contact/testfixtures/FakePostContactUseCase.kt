package com.gchristov.personal.contact.testfixtures

import arrow.core.Either
import com.gchristov.personal.contact.domain.usecase.PostContactUseCase
import kotlin.test.assertEquals

class FakePostContactUseCase(
    private val invocationResult: Either<Throwable, Unit> = Either.Right(Unit),
) : PostContactUseCase {
    private var invocations = 0
    private var lastName: String? = null
    private var lastEmail: String? = null
    private var lastMessage: String? = null

    override suspend fun invoke(
        name: String,
        email: String,
        message: String,
    ): Either<Throwable, Unit> {
        lastName = name
        lastEmail = email
        lastMessage = message
        invocations++
        return invocationResult
    }

    fun assertInvokedOnce() = assertEquals(expected = 1, actual = invocations)

    fun assertArgs(name: String, email: String, message: String) {
        assertEquals(expected = name, actual = lastName)
        assertEquals(expected = email, actual = lastEmail)
        assertEquals(expected = message, actual = lastMessage)
    }
}
