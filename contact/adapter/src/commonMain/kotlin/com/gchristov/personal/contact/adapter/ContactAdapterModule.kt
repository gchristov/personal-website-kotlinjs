package com.gchristov.personal.contact.adapter

import co.touchlab.kermit.Logger
import com.gchristov.personal.common.kotlin.JsonSerializer
import com.gchristov.personal.common.kotlin.di.DiModule
import com.gchristov.personal.contact.adapter.http.PostContactHttpHandler
import com.gchristov.personal.contact.domain.usecase.PostContactUseCase
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

object ContactAdapterModule : DiModule() {
    override fun name() = "contact-adapter"

    override fun bindDependencies(builder: DI.Builder) {
        builder.apply {
            bindSingleton {
                providePostContactHttpHandler(
                    jsonSerializer = instance(),
                    log = instance(),
                    postContactUseCase = instance(),
                )
            }
        }
    }

    private fun providePostContactHttpHandler(
        jsonSerializer: JsonSerializer.Default,
        log: Logger,
        postContactUseCase: PostContactUseCase,
    ): PostContactHttpHandler = PostContactHttpHandler(
        dispatcher = Dispatchers.Default,
        jsonSerializer = jsonSerializer,
        log = log,
        postContactUseCase = postContactUseCase,
    )
}
