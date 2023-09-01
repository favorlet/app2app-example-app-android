package io.fingerlabs.lib.app2app.common

enum class App2AppAction(val value: String) {
    CONNECT_WALLET(value = "connectWallet"),
    SIGN_MESSAGE(value = "signMessage"),
    CONNECT_WALLET_AND_SIGN_MESSAGE(value = "connectWalletAndSignMessage"),
    SEND_COIN(value = "sendCoin"),
    EXECUTE_CONTRACT_WITH_ENCODED(value = "executeContractWithEncoded")
}