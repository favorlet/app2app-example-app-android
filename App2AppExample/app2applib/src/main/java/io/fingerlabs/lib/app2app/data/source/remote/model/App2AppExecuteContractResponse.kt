package io.fingerlabs.lib.app2app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class App2AppExecuteContractResponse(
    @SerialName("requestId")
    val requestId: String? = null,

    @SerialName("expiredAt")
    val expiredAt: String? = null,

    @SerialName("error")
    val error: App2AppError? = null,
)
