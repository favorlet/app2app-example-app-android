package io.fingerlabs.lib.app2app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class App2AppReceiptResponse(

    @SerialName("requestId")
    val requestId: String? = null,

    @SerialName("expiredAt")
    val expiredAt: String? = null,

    @SerialName("action")
    val action: String? = null,

    @SerialName("chainId")
    val chainId: Int? = null,

    @SerialName("connectWallet")
    val connectWallet: ConnectWallet? = null,

    @SerialName("signMessage")
    val signMessage: SignMessage? = null,

    @SerialName("transactions")
    val transactions: List<Transaction>? = null,

    @SerialName("error")
    val error: App2AppError? = null,
) {

    @Serializable
    data class ConnectWallet(
        @SerialName("status")
        val status: String? = null,

        @SerialName("address")
        val address: String? = null,

        @SerialName("errorMessage")
        val errorMessage: String? = null,
    )

    @Serializable
    data class SignMessage(
        @SerialName("status")
        val status: String? = null,

        @SerialName("signature")
        val signature: String? = null,

        @SerialName("errorMessage")
        val errorMessage: String? = null,
    )

    @Serializable
    data class Transaction(
        @SerialName("status")
        val status: String? = null,

        @SerialName("txHash")
        val txHash: String? = null,

        @SerialName("errorMessage")
        val errorMessage: String? = null,
    )


}

