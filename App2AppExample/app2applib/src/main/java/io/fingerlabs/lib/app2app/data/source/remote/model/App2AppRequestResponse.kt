package io.fingerlabs.lib.app2app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class App2AppRequestResponse(
    @SerialName("requestId")
    val requestId: String?,

    @SerialName("expiredAt")
    val expiredAt: String?,

    @SerialName("err")
    val err: Err?,

) {

    @Serializable
    data class Err(
        @SerialName("message")
        val errorMessage: String?,

        @SerialName("code")
        val code: String?,

    ): Exception(errorMessage ?: "")

}