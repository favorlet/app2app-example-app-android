package io.fingerlabs.lib.app2app.data.repository

import android.util.Log
import io.fingerlabs.lib.app2app.data.source.remote.api.App2AppApi
import io.fingerlabs.lib.app2app.data.source.remote.model.*

internal class App2AppRepository constructor(
    private val app2appApi: App2AppApi = App2AppApi()
) {

    /**
     * 지갑연결 요청.
     */
    suspend fun requestConnectWallet(
        request: App2AppConnectWalletRequest
    ): App2AppRequestResponse {
        return app2appApi.requestConnectWallet(request)
    }


    /**
     * 메시지 서명 요청.
     */
    suspend fun requestSignMessage(
        request: App2AppSignMessageRequest
    ): App2AppRequestResponse {
        return app2appApi.requestSignMessage(request)
    }


    /**
     * 지갑 연결 & 메시지 서명 요청.
     */
    suspend fun requestConnectWalletSignMessage(
        request: App2AppConnectWalletAndSignMessageRequest
    ): App2AppRequestResponse {
        return app2appApi.requestConnectWalletSignMessage(request)
    }


    /**
     * 코인 전송 요청.
     */
    suspend fun requestSendCoin(
        request: App2AppSendCoinRequest
    ): App2AppRequestResponse {
        return app2appApi.requestSendCoin(request)
    }


    /**
     * 컨트랙트 실행 요청.
     */
    suspend fun requestExecuteContract(
        request: App2AppExecuteContractRequest
    ): App2AppRequestResponse {
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