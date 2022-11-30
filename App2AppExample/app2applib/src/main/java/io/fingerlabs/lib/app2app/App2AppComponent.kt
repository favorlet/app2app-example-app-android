package io.fingerlabs.lib.app2app

import io.fingerlabs.lib.app2app.data.source.remote.model.*
import io.fingerlabs.lib.app2app.domain.usecase.*

class App2AppComponent {
    private val requestConnectWalletUseCase = RequestConnectWalletUseCase()
    private val requestSignMessageUseCase = RequestSignMessageUseCase()
    private val requestSendCoinUseCase = RequestSendCoinUseCase()
    private var requestExecuteContractUseCase = RequestExecuteContractUseCase()
    private var receiptUseCase = ReceiptUseCase()


    suspend fun requestConnectWallet(
        request: App2AppConnectWalletRequest
    ): App2AppRequestResponse = requestConnectWalletUseCase.invoke(request)


    suspend fun requestSignMessage(
        request: App2AppSignMessageRequest
    ): App2AppRequestResponse = requestSignMessageUseCase.invoke(request)


    suspend fun requestSendCoin(
        request: App2AppSendCoinRequest
    ): App2AppRequestResponse = requestSendCoinUseCase.invoke(request)


    suspend fun requestExecuteContract(
        request: App2AppExecuteContractRequest
    ): App2AppRequestResponse = requestExecuteContractUseCase.invoke(request)


    suspend fun receipt(
        requestId: String
    ): App2AppReceiptResponse = receiptUseCase.invoke(requestId)

}