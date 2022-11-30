package io.fingerlabs.lib.app2app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class App2AppBlockChainApp(
    @SerialName("name")
    val name: String,

    @SerialName("successAppLink")
    val successAppLink: String,

    @SerialName("failAppLink")
    val failAppLink: String
)