package com.gchristov.personal.common.network.http

import arrow.core.Either

interface Handler {
    suspend fun initialise(): Either<Throwable, Unit>
}
