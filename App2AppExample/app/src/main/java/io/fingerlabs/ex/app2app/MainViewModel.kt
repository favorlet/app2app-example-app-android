package io.fingerlabs.ex.app2app

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.fingerlabs.ex.app2app.common.eventwrapper.Event
import io.fingerlabs.lib.app2app.App2AppComponent
import io.fingerlabs.lib.app2app.common.App2AppAction
import io.fingerlabs.lib.app2app.common.App2AppStatus
import io.fingerlabs.lib.app2app.data.source.remote.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainViewModel constructor(
    private val app2AppComponent: App2AppComponent = App2AppComponent()
): ViewModel() {
    val app2AppRequestId = MutableLiveData<Event<String>>()
    val connectedAddress = MutableLiveData<String>()
    val signatureHash = MutableLiveData<String>()
    val resultSendCoin = MutableLiveData<Event<String>>()
    val resultExecuteContract = MutableLiveData<Event<String>>()

    val progress = MutableLiveData<Event<Boolean>>()
    val isConnectedWallet = MutableLiveData<Event<Boolean>>()

    fun requestConnectWallet(chainId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(Event(true))
            runCatching {
                val request = App2AppConnectWalletRequest(
                        action = App2AppAction.CONNECT_WALLET.value,
                        chainId = chainId,
                        blockChainApp = App2AppBlockChainApp(
                                name = "App2App Sample",
                                successAppLink = "",
                                failAppLink = "",
                        )
                )
                app2AppComponent.requestConnectWallet(request)
            }.onSuccess {
                if (it.err == null && !(it.requestId.isNullOrEmpty())) {
                    app2AppRequestId.postValue(Event(it.requestId!!))
                    isConnectedWallet.postValue(Event(true))
                }
                progress.postValue(Event(false))
            }.onFailure {
                progress.postValue(Event(false))
            }
        }
    }


    fun requestSignMessage(chainId: Int, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(Event(true))
            runCatching {
                val request = App2AppSignMessageRequest(
                    action = App2AppAction.SIGN_MESSAGE.value,
                    chainId = chainId,
                    blockChainApp = App2AppBlockChainApp(
                        name = "App2App Sample",
                        successAppLink = "",
                        failAppLink = "",
                    ),
                    signMessage = App2AppSignMessage(
                        from = connectedAddress.value ?: "",
                        value = message,
                    )
                )
                app2AppComponent.requestSignMessage(request)
            }.onSuccess {
                if (it.err == null && !(it.requestId.isNullOrEmpty())) {
                    app2AppRequestId.postValue(Event(it.requestId!!))
                }
                progress.postValue(Event(false))
            }.onFailure {
                progress.postValue(Event(false))
            }
        }
    }


    fun requestSendCoin(chainId: Int, toAddress: String, amount: String) {
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(Event(true))
            runCatching {
                val request = App2AppSendCoinRequest(
                    action = App2AppAction.SEND_COIN.value,
                    chainId = chainId,
                    blockChainApp = App2AppBlockChainApp(
                        name = "App2App Sample",
                        successAppLink = "",
                        failAppLink = "",
                    ),
                    transactions = listOf(
                        App2AppTransaction(
                            from = connectedAddress.value ?: "",
                            to = toAddress,
                            value = amount,
                        )
                    )
                )
                app2AppComponent.requestSendCoin(request)
            }.onSuccess {
                if (it.err == null && !(it.requestId.isNullOrEmpty())) {
                    app2AppRequestId.postValue(Event(it.requestId!!))
                }
                progress.postValue(Event(false))
            }.onFailure {
                progress.postValue(Event(false))
            }
        }
    }


    fun requestExecuteContract(
        chainId: Int,
        contractAddress: String,
        abi: String,
        params: String,
        value: String,
        functionName: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(Event(true))
            runCatching {
                val request = App2AppExecuteContractRequest(
                    action = App2AppAction.EXECUTE_CONTRACT.value,
                    chainId = chainId,
                    blockChainApp = App2AppBlockChainApp(
                        name = "App2App Sample",
                        successAppLink = "",
                        failAppLink = "",
                    ),
                    transactions = listOf(
                        App2AppTransaction(
                            from = connectedAddress.value ?: "",
                            to = contractAddress,
                            abi = abi,
                            value = value,
                            params = params,
                            functionName = functionName
                        )
                    )
                )
                app2AppComponent.requestExecuteContract(request)
            }.onSuccess {
                if (it.err == null && !(it.requestId.isNullOrEmpty())) {
                    app2AppRequestId.postValue(Event(it.requestId!!))
                }
                progress.postValue(Event(false))
            }.onFailure {
                progress.postValue(Event(false))
            }
        }
    }



    fun execute(activityContext: Context, requestId: String) {
        viewModelScope.launch {
            app2AppComponent.execute(activityContext, requestId)
        }
    }


    fun receipt() {
        viewModelScope.launch(Dispatchers.IO) {
            if (app2AppRequestId.value?.peekContent().isNullOrEmpty()) return@launch

            progress.postValue(Event(true))
            val requestId = app2AppRequestId.value?.getContentIfNotHandled()
            repeat(5) {
                runCatching {
                    requestId?.let { app2AppComponent.receipt(it) }
                }.onSuccess {
                    when (it?.action) {
                        App2AppAction.CONNECT_WALLET.value -> {
                            // 지갑연결
                            if (it?.connectWallet?.status == App2AppStatus.SUCCEED.value) {
                                val address = it?.connectWallet?.address ?: "-"
                                connectedAddress.postValue(address)
                            }
                        }
                        App2AppAction.SIGN_MESSAGE.value -> {
                            // 메시지 서명
                            if (it?.signMessage?.status == App2AppStatus.SUCCEED.value) {
                                val signature = it?.signMessage?.signature ?: "-"
                                signatureHash.postValue(signature)
                            }
                        }
                        App2AppAction.SEND_COIN.value -> {
                            // 코인 전송
                            if (it?.transactions?.first()?.status == App2AppStatus.SUCCEED.value) {
                                val status = it?.transactions?.first()?.status ?: "-"
                                resultSendCoin.postValue(Event(status))
                            }
                        }
                        App2AppAction.EXECUTE_CONTRACT.value -> {
                            // 컨트랙트 실행
                            if (it?.transactions?.first()?.status == App2AppStatus.SUCCEED.value) {
                                val status = it?.transactions?.first()?.status ?: "-"
                                resultExecuteContract.postValue(Event(status))
                            }
                        }
                        else -> {}
                    }
                    if (it != null) {
                        progress.postValue(Event(false))
                        return@repeat
                    }
                }.onFailure {
                    progress.postValue(Event(false))
                }
                delay(2000)
            }
            progress.postValue(Event(false))
        }
    }

}