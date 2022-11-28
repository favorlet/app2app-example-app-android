package io.fingerlabs.ex.app2app.data.source.model

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