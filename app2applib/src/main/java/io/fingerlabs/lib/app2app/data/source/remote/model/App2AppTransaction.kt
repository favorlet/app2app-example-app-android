package io.fingerlabs.lib.app2app.data.source.remote.model

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

    @Deprecated("1.0.5 이상부터는 지원하지 않습니다.")
    @SerialName("abi")
    val abi: String? = null,

    @Deprecated("1.0.5 이상부터는 지원하지 않습니다.")
    @SerialName("params")
    val params: String? = null,

    @Deprecated("1.0.5 이상부터는 지원하지 않습니다.")
    @SerialName("functionName")
    val functionName: String? = null,

    @SerialName("data")
    val data: String? = null,

    @SerialName("gasLimit")
    val gasLimit: String? = null
)
