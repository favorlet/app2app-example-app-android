package io.fingerlabs.lib.app2app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class App2AppConnectWalletAndSignMessageRequest(
    @SerialName("action")
    val action: String,

    @SerialName("chainId")
    val chainId: Int,

    @SerialName("blockChainApp")
    val blockChainApp: App2AppBlockChainApp,

    @SerialName("connectWalletAndSignMessage")
    val connectWalletAndSignMessage: App2AppConnectWalletAndSignMessage,
)
