package io.fingerlabs.lib.app2app.common

object Constant {

    const val BASEURL_APP2APP = "bridge.favorlet.link"

    enum class Action(val value: String) {
        CONNECT_WALLET(value = "connectWallet"),
        SIGN_MESSAGE(value = "signMessage"),
        SEND_COIN(value = "sendCoin"),
        EXECUTE_CONTRACT(value = "executeContract"),
    }


    enum class Status(val value: String) {
        REQUESTED(value = "requested"),
        EXECUTED(value = "executed"),
        REVERTED(value = "reverted"),
        FAILED(value = "failed"),
        CANCELED(value = "canceled"),
        SUCCEED(value = "succeed"),
    }


}