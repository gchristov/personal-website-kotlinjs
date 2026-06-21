package com.gchristov.personal.contact.service

import com.gchristov.personal.common.kotlin.di.DiModule
import com.gchristov.personal.contact.domain.model.Environment
import org.kodein.di.DI
import org.kodein.di.bindSingleton

internal class ContactServiceModule(private val environment: Environment) : DiModule() {
    override fun name() = "contact-service"

    override fun bindDependencies(builder: DI.Builder) {
        builder.apply {
            bindSingleton { environment }
        }
    }
}
