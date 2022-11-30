package io.fingerlabs.lib.app2app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class App2AppError(
    @SerialName("message")
    val message: String? = null,

    @SerialName("code")
    val code: String? = null,
)
