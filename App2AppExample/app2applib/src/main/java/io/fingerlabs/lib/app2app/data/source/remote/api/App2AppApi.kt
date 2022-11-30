package io.fingerlabs.lib.app2app.data.source.remote.api

import io.fingerlabs.lib.app2app.data.source.remote.KtorManager
import io.fingerlabs.lib.app2app.data.source.remote.model.*
import io.ktor.client.*
import io.ktor.client.request.*

class App2AppApi constructor(
    private val httpClient: HttpClient = KtorManager.newInstance()
) {

    suspend fun requestConnectWallet(
        request: App2AppConnectWalletRequest
    ): App2AppConnectWalletResponse =
        httpClient.post(path = "/request", body = request)


    suspend fun requestSignMessage(
        request: App2AppSignMessageRequest
    ): App2AppSignMessageResponse =
        httpClient.post(path = "/request", body = request)

    suspend fun requestSendCoin(
        request: App2AppSendCoinRequest
    ): App2AppSendCoinResponse =
        httpClient.post(path = "/request", body = request)

    suspend fun requestExecuteContract(
        request: App2AppExecuteContractRequest
    ): App2AppExecuteContractResponse =
        httpClient.post(path = "/request", body = request)


    suspend fun requestReceipt(
        requestId: String
    ): App2AppReceiptResponse =
        httpClient.get(path = "/receipt") {
            parameter("requestId", requestId)
        }



}