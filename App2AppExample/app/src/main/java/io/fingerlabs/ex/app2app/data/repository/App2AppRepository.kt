package io.fingerlabs.ex.app2app.data.repository

import io.fingerlabs.ex.app2app.data.source.api.App2AppApi
import io.fingerlabs.ex.app2app.data.source.model.*
import javax.inject.Inject

class App2AppRepository @Inject constructor(
    private val app2AppApi: App2AppApi
) {

    suspend fun requestConnectWallet(
        request: App2AppConnectWalletRequest
    ): App2AppConnectWalletResponse {
        return app2AppApi.requestConnectWallet(request)
    }

    suspend fun requestSignMessage(
        request: App2AppSignMessageRequest
    ): App2AppSignMessageResponse {
        return app2AppApi.requestSignMessage(request)
    }

    suspend fun requestSendCoin(
        request: App2AppSendCoinRequest
    ): App2AppSendCoinResponse {
        return app2AppApi.requestSendCoin(request)
    }

    suspend fun requestExecuteContract(
        request: App2AppExecuteContractRequest
    ): App2AppExecuteContractResponse {
        return app2AppApi.requestExecuteContract(request)
    }

    suspend fun requestReceipt(
        requestId: String
    ): App2AppReceiptResponse {
        return app2AppApi.requestReceipt(requestId)
    }

}