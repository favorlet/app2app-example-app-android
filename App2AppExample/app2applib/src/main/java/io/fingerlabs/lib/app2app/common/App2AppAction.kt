package io.fingerlabs.lib.app2app.common

enum class App2AppAction(val value: String) {
    CONNECT_WALLET(value = "connectWallet"),
    SIGN_MESSAGE(value = "signMessage"),
    SEND_COIN(value = "sendCoin"),
    EXECUTE_CONTRACT(value = "executeContractWithEncoded"),
}