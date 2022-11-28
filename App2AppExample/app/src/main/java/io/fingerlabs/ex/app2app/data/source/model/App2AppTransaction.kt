package io.fingerlabs.ex.app2app.data.source.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class App2AppTransaction(
    @SerialName("from")
    val from: String,

    @SerialName("to")
    val to: String,

    @SerialName("value")
    val value: String? = null,

    @SerialName("abi")
    val abi: String? = null,

    @SerialName("params")
    val params: String? = null,

    @SerialName("functionName")
    val functionName: String? = null,
)
