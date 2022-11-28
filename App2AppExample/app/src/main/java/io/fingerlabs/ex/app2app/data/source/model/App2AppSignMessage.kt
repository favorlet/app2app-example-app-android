package io.fingerlabs.ex.app2app.data.source.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class App2AppSignMessage(
    @SerialName("from")
    val from: String,

    @SerialName("value")
    val value: String,
)
