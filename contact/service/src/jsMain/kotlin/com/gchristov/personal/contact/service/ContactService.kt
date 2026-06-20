package com.gchristov.personal.contact.service

import arrow.core.Either
import arrow.core.flatMap
import co.touchlab.kermit.Logger
import com.gchristov.personal.common.kotlin.CommonKotlinModule
import com.gchristov.personal.common.kotlin.debug
import com.gchristov.personal.common.kotlin.di.DiGraph
import com.gchristov.personal.common.kotlin.di.inject
import com.gchristov.personal.common.kotlin.di.registerModules
import com.gchristov.personal.common.kotlin.process
import com.gchristov.personal.common.monitoring.CommonMonitoringModule
import com.gchristov.personal.common.monitoring.MonitoringLogWriter
import com.gchristov.personal.common.network.CommonNetworkModule
import com.gchristov.personal.common.network.http.HttpService
import com.gchristov.personal.common.slack.CommonSlackModule
import com.gchristov.personal.contact.adapter.ContactAdapterModule
import com.gchristov.personal.contact.adapter.http.PostContactHttpHandler
import com.gchristov.personal.contact.domain.ContactDomainModule
import com.gchristov.personal.contact.domain.model.Environment

suspend fun main() {
    // Ignore default Node arguments
    val environment = Environment.of(process.argv.slice(2) as Array<String>)
    val tag = "ContactService"

    setupDi(environment = environment)
        .flatMap { setupMonitoring() }
        .flatMap { setupService(environment.port) }
        .flatMap { startService(it) }
        .fold(ifLeft = { error ->
            val log = DiGraph.inject<Logger>()
            log.debug(tag, "Error starting${error.message?.let { ": $it" } ?: ""}")
            error.printStackTrace()
        }, ifRight = {
            val log = DiGraph.inject<Logger>()
            log.debug(tag, "Started: environment=$environment")
        })
}

private fun setupDi(environment: Environment): Either<Throwable, Unit> {
    DiGraph.registerModules(
        listOf(
            CommonKotlinModule.module,
            CommonNetworkModule.module,
            CommonSlackModule.module,
            CommonMonitoringModule.module,
            ContactDomainModule.module,
            ContactAdapterModule.module,
            ContactServiceModule(environment).module,
        )
    )
    return Either.Right(Unit)
}

private fun setupMonitoring(): Either<Throwable, Unit> {
    DiGraph.inject<MonitoringLogWriter>().apply {
        Logger.addLogWriter(this)
    }
    return Either.Right(Unit)
}

private suspend fun setupService(port: Int): Either<Throwable, HttpService> {
    val handlers = listOf(
        DiGraph.inject<PostContactHttpHandler>(),
    )
    val service = DiGraph.inject<HttpService>()
    return service.initialise(
        handlers = handlers,
        port = port,
    ).flatMap { Either.Right(service) }
}

private suspend fun startService(service: HttpService): Either<Throwable, Unit> = service
    .start()
    .flatMap { Either.Right(Unit) }
