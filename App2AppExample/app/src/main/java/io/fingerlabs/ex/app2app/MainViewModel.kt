package io.fingerlabs.ex.app2app

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.fingerlabs.ex.app2app.common.eventwrapper.Event
import io.fingerlabs.lib.app2app.App2AppComponent
import io.fingerlabs.lib.app2app.common.App2AppAction
import io.fingerlabs.lib.app2app.common.App2AppStatus
import io.fingerlabs.lib.app2app.data.source.remote.model.*
import kotlinx.coroutines.*


class MainViewModel constructor(
    private val app2AppComponent: App2AppComponent = App2AppComponent()
) : ViewModel() {
    val app2AppRequestId = MutableLiveData<Event<String>>()
    val connectedAddress = MutableLiveData<String>()
    val signatureHash = MutableLiveData<String>()
    val connectedAddress2 = MutableLiveData<String>()
    val signatureHash2 = MutableLiveData<String>()
    val resultSendCoin = MutableLiveData<Event<String>>()
    val resultExecuteContractWithEncoded = MutableLiveData<Event<String>>()

    val progress = MutableLiveData<Event<Boolean>>()
    val isConnectedWallet = MutableLiveData<Event<Boolean>>()
    val receivedChainId = MutableLiveData<Int>()

    val errorToast = MutableLiveData<Event<String>>()

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
                } else if (it.err != null) {
                    errorToast.postValue(Event("[${it.err?.code}] ${it.err?.errorMessage}"))
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
                } else if (it.err != null) {
                    errorToast.postValue(Event("[${it.err?.code}] ${it.err?.errorMessage}"))
                }
                progress.postValue(Event(false))
            }.onFailure {
                progress.postValue(Event(false))
            }
        }
    }


    fun requestConnectWalletSignMessage(chainId: Int, message: String) {

        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(Event(true))
            runCatching {

                val request = App2AppConnectWalletAndSignMessageRequest(
                    action = App2AppAction.CONNECT_WALLET_AND_SIGN_MESSAGE.value,
                    chainId = chainId,
                    blockChainApp = App2AppBlockChainApp(
                        name = "App2App Sample",
                        successAppLink = "",
                        failAppLink = "",
                    ),
                    connectWalletAndSignMessage = App2AppConnectWalletAndSignMessage(
                        value = message,
                    )
                )
                app2AppComponent.requestConnectWalletAndSignMessage(request)
            }.onSuccess {
                if (it.err == null && !(it.requestId.isNullOrEmpty())) {
                    app2AppRequestId.postValue(Event(it.requestId!!))
                } else if (it.err != null) {
                    errorToast.postValue(Event("[${it.err?.code}] ${it.err?.errorMessage}"))
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
                } else if (it.err != null) {
                    errorToast.postValue(Event("[${it.err?.code}] ${it.err?.errorMessage}"))
                }
                progress.postValue(Event(false))
            }.onFailure {
                progress.postValue(Event(false))
            }
        }
    }


    fun requestExecuteContractWithEncoded(
        chainId: Int,
        contractAddress: String,
        data: String,
        value: String,
        gasLimit: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            progress.postValue(Event(true))
            runCatching {
                val request = App2AppExecuteContractRequest(
                    action = App2AppAction.EXECUTE_CONTRACT_WITH_ENCODED.value,
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
                            data = data,
                            value = value,
                            gasLimit = gasLimit.ifEmpty { null }
                        )
                    )
                )
                app2AppComponent.requestExecuteContract(request)
            }.onSuccess {
                if (it.err == null && !(it.requestId.isNullOrEmpty())) {
                    app2AppRequestId.postValue(Event(it.requestId!!))
                } else if (it.err != null) {
                    errorToast.postValue(Event("[${it.err?.code}] ${it.err?.errorMessage}"))
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
            val requestId = app2AppRequestId.value?.peekContent()

            runCatching {
                requestId?.let { app2AppComponent.receipt(it) }
            }.onSuccess {

                when (it?.action) {
                    App2AppAction.CONNECT_WALLET.value -> {
                        // 지갑연결
                        if (it.connectWallet?.status == App2AppStatus.SUCCEED.value) {
                            val address = it.connectWallet?.address ?: "-"
                            connectedAddress.postValue(address)
                            val chainId = it.chainId ?: -1
                            receivedChainId.postValue(chainId)
                        }
                    }

                    App2AppAction.SIGN_MESSAGE.value -> {
                        // 메시지 서명
                        if (it.signMessage?.status == App2AppStatus.SUCCEED.value) {
                            val signature = it.signMessage?.signature ?: "-"
                            signatureHash.postValue(signature)
                        }
                    }

                    App2AppAction.CONNECT_WALLET_AND_SIGN_MESSAGE.value -> {
                        //지갑연결 & 메세지 서명
                        if (it.connectWalletAndSignMessage?.status == App2AppStatus.SUCCEED.value) {

                            val address = it.connectWalletAndSignMessage?.address ?: "-"
                            val signature = it.connectWalletAndSignMessage?.signature ?: "-"

                            connectedAddress2.postValue(address)
                            signatureHash2.postValue(signature)
                        }
                    }

                    App2AppAction.SEND_COIN.value -> {
                        // 코인 전송
                        if (it.transactions?.first()?.status == App2AppStatus.SUCCEED.value) {
                            val status = it.transactions?.first()?.status ?: "-"
                            resultSendCoin.postValue(Event(status))
                        }
                    }

                    App2AppAction.EXECUTE_CONTRACT_WITH_ENCODED.value -> {
                        // 컨트랙트 실행
                        if (it.transactions?.first()?.status == App2AppStatus.SUCCEED.value) {
                            val status = it.transactions?.first()?.status ?: "-"
                            resultExecuteContractWithEncoded.postValue(Event(status))
                        }
                    }

                    else -> {}
                }
                if (it != null) {
                    progress.postValue(Event(false))
                }
            }.onFailure {
                progress.postValue(Event(false))
            }
            progress.postValue(Event(false))
        }
    }


    fun showErrorToast(message: String) {
        viewModelScope.launch {
            errorToast.postValue(Event(message))
        }
    }
}