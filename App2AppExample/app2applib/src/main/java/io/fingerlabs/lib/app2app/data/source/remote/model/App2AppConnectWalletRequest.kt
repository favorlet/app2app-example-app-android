package io.fingerlabs.lib.app2app.data.source.remote.model


class App2AppConnectWalletRequest(
    action: String,
    chainId: Int,
    blockChainApp: App2AppBlockChainApp
): App2AppRequest(action, chainId, blockChainApp)
