package com.gchristov.personal.contact.domain

import co.touchlab.kermit.Logger
import com.gchristov.personal.common.kotlin.di.DiModule
import com.gchristov.personal.common.slack.SlackSender
import com.gchristov.personal.contact.domain.model.ContactConfig
import com.gchristov.personal.contact.domain.usecase.PostContactUseCase
import com.gchristov.personal.contact.domain.usecase.RealPostContactUseCase
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

object ContactDomainModule : DiModule() {
    override fun name() = "contact-domain"

    override fun bindDependencies(builder: DI.Builder) {
        builder.apply {
            bindSingleton {
                provideContactConfig()
            }
            bindProvider {
                providePostContactUseCase(
                    log = instance(),
                    slackSender = instance(),
                    contactConfig = instance(),
                )
            }
        }
    }

    private fun provideContactConfig(): ContactConfig = ContactConfig.fromBuildConfig()

    private fun providePostContactUseCase(
        log: Logger,
        slackSender: SlackSender,
        contactConfig: ContactConfig,
    ): PostContactUseCase = RealPostContactUseCase(
        dispatcher = Dispatchers.Default,
        log = log,
        slackSender = slackSender,
        contactConfig = contactConfig,
    )
}
