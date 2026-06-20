package com.gchristov.personal.contact.domain.model

import com.gchristov.personal.common.kotlin.parseMainArgs

data class Environment(
    val port: Int,
) {
    companion object {
        fun of(args: Array<String>) = with(parseMainArgs(args)) {
            Environment(
                port = requireNotNull(this["-port"]) { "-port not specified." }.first().toInt(),
            )
        }
    }
}
