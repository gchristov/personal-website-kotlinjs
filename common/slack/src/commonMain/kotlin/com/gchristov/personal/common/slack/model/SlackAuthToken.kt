package com.gchristov.personal.common.slack.model

data class SlackAuthToken(
    val id: String,
    val scope: String,
    val token: String,
    val teamId: String,
    val teamName: String,
)
