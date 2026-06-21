package com.gchristov.personal.common.slack.api.mapper

import com.gchristov.personal.common.slack.api.model.ApiSlackAuthResponse
import com.gchristov.personal.common.slack.model.SlackAuthToken

// TODO: Consider better differentiation between the token types
internal fun ApiSlackAuthResponse.toAuthToken() = if (authedUser?.accessToken != null) {
    // User token
    SlackAuthToken(
        id = authedUser.id,
        scope = authedUser.scope!!,
        token = authedUser.accessToken,
        teamId = team!!.id,
        teamName = team.name,
    )
} else {
    // Bot token
    SlackAuthToken(
        id = botUserId!!,
        scope = scope!!,
        token = accessToken!!,
        teamId = team!!.id,
        teamName = team.name,
    )
}
