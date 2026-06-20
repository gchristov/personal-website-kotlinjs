package com.gchristov.personal.contact.domain.usecase

import arrow.core.Either
import co.touchlab.kermit.Logger
import com.gchristov.personal.common.kotlin.debug
import com.gchristov.personal.common.slack.SlackSender
import com.gchristov.personal.common.slack.model.SlackMessage
import com.gchristov.personal.contact.domain.model.ContactConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface PostContactUseCase {
    suspend operator fun invoke(
        name: String,
        email: String,
        message: String,
    ): Either<Throwable, Unit>
}

internal class RealPostContactUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val log: Logger,
    private val slackSender: SlackSender,
    private val contactConfig: ContactConfig,
) : PostContactUseCase {
    private val tag = this::class.simpleName

    override suspend fun invoke(
        name: String,
        email: String,
        message: String,
    ): Either<Throwable, Unit> = withContext(dispatcher) {
        log.debug(tag, "Posting contact message to Slack")
        slackSender.postMessageToUrl(
            url = contactConfig.slackWebhookUrl,
            message = SlackMessage(
                text = "*New message from $name* ($email)\n\n$message",
            ),
        )
    }
}
