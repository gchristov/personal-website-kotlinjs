package com.gchristov.personal.common.network

import co.touchlab.kermit.Logger
import com.gchristov.personal.common.kotlin.JsonSerializer
import com.gchristov.personal.common.kotlin.di.DiModule
import com.gchristov.personal.common.network.http.HttpService
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

object CommonNetworkModule : DiModule() {
    override fun name() = "common-network"

    override fun bindDependencies(builder: DI.Builder) {
        builder.apply {
            bindSingleton { provideHtmlNetworkClient() }
            bindSingleton {
                provideJsonNetworkClient(
                    jsonSerializer = instance(),
                )
            }
            bindProvider { provideHttpService(log = instance()) }
        }
    }

    private fun provideHtmlNetworkClient() = NetworkClient.Html

    private fun provideJsonNetworkClient(
        jsonSerializer: JsonSerializer.Default,
    ) = NetworkClient.Json(
        jsonSerializer = jsonSerializer,
    )
}

expect fun provideHttpService(log: Logger): HttpService
