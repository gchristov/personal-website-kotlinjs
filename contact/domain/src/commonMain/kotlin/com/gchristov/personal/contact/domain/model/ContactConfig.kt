package com.gchristov.personal.contact.domain.model

import com.gchristov.personal.contact.domain.BuildConfig

data class ContactConfig(
    val slackWebhookUrl: String,
) {
    companion object {
        fun fromBuildConfig() = ContactConfig(
            slackWebhookUrl = BuildConfig.CONTACT_SLACK_WEBHOOK_URL,
        )
    }
}
