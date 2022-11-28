package io.fingerlabs.ex.app2app.data.source.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class App2AppSendCoinResponse(
    @SerialName("requestId")
    val requestId: String? = null,

    @SerialName("expiredAt")
    val expiredAt: String? = null,

    @SerialName("error")
    val error: App2AppError? = null,
)
