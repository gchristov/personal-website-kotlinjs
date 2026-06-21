package com.gchristov.personal.contact.adapter.http.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ApiContact(
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
    @SerialName("message") val message: String,
)
