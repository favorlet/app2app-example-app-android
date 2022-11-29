package io.fingerlabs.ex.app2app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.fingerlabs.ex.app2app.common.eventwrapper.Event
import io.fingerlabs.ex.app2app.data.repository.App2AppRepository
import io.fingerlabs.ex.app2app.data.source.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        private val app2AppRepository: App2AppRepository
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
                        action = "connectWallet",
                        chainId = chainId,
                        blockChainApp = App2AppBlockChainApp(
                                name = "App2App Sample",
                                successAppLink = "",
                                failAppLink = "",
                        )
                )
                app2AppRepository.requestConnectWallet(request)
            }.onSuccess {
                if (it.error == null && !(it.requestId.isNullOrEmpty())) {
                    app2AppRequestId.postValue(Event(it.requestId))
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
                    action = "signMessage",
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
                app2AppRepository.requestSignMessage(request)
            }.onSuccess {
                if (it.error == null && !(it.requestId.isNullOrEmpty())) {
                    app2AppRequestId.postValue(Event(it.requestId))
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
                    action = "sendCoin",
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
                app2AppRepository.requestSendCoin(request)
            }.onSuccess {
                if (it.error == null && !(it.requestId.isNullOrEmpty())) {
                    app2AppRequestId.postValue(Event(it.requestId))
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
                    action = "executeContract",
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
                app2AppRepository.requestExecuteContract(request)
            }.onSuccess {
                if (it.error == null && !(it.requestId.isNullOrEmpty())) {
                    app2AppRequestId.postValue(Event(it.requestId))
                }
                progress.postValue(Event(false))
            }.onFailure {
                progress.postValue(Event(false))
            }
        }
    }


    fun requestReceipt() {
        viewModelScope.launch(Dispatchers.IO) {
            if (app2AppRequestId.value?.peekContent().isNullOrEmpty()) return@launch

            progress.postValue(Event(true))
            val requestId = app2AppRequestId.value?.getContentIfNotHandled()
            repeat(5) {
                runCatching {
                    requestId?.let { app2AppRepository.requestReceipt(it) }
                }.onSuccess {
                    when (it?.action) {
                        "connectWallet" -> {
                            val address = it?.connectWallet?.address ?: "-"
                            connectedAddress.postValue(address)
                        }
                        "signMessage" -> {
                            val signature = it?.signMessage?.signature ?: "-"
                            signatureHash.postValue(signature)
                        }
                        "sendCoin" -> {
                            val status = it?.transactions?.first()?.status ?: "-"
                            resultSendCoin.postValue(Event(status))
                        }
                        "executeContract" -> {
                            val status = it?.transactions?.first()?.status ?: "-"
                            resultExecuteContract.postValue(Event(status))
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