package io.fingerlabs.lib.app2app.data.repository

import io.fingerlabs.lib.app2app.data.source.remote.api.App2AppApi
import io.fingerlabs.lib.app2app.data.source.remote.model.*

class App2AppRepository constructor(
    private val app2appApi: App2AppApi = App2AppApi()
) {

    /**
     * 지갑연결 요청.
     */
    suspend fun requestConnectWallet(
        request: App2AppConnectWalletRequest
    ): App2AppConnectWalletResponse {
        return app2appApi.requestConnectWallet(request)
    }


    /**
     * 메시지 서명 요청.
     */
    suspend fun requestSignMessage(
        request: App2AppSignMessageRequest
    ): App2AppSignMessageResponse {
        return app2appApi.requestSignMessage(request)
    }


    /**
     * 코인전송 요청.
     */
    suspend fun requestSendCoin(
        request: App2AppSendCoinRequest
    ): App2AppSendCoinResponse {
        return app2appApi.requestSendCoin(request)
    }


    /**
     * 컨트랙트 실행 요청.
     */
    suspend fun requestExecuteContract(
        request: App2AppExecuteContractRequest
    ): App2AppExecuteContractResponse {
        return app2appApi.requestExecuteContract(request)
    }


    /**
     * app2app 연동 결과 요청.
     */
    suspend fun requestReceipt(
        requestId: String
    ): App2AppReceiptResponse {
        return app2appApi.requestReceipt(requestId)
    }


}