package io.fingerlabs.ex.app2app.data.source.api

import io.fingerlabs.ex.app2app.common.di.SingletonModule
import io.fingerlabs.ex.app2app.data.source.model.*
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class App2AppApi @Inject constructor(
    @SingletonModule.App2AppHttpClient private val httpClient: HttpClient
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