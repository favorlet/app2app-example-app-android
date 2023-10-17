package io.fingerlabs.ex.app2app

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.fingerlabs.ex.app2app.common.SharedUtil
import io.fingerlabs.ex.app2app.common.eventwrapper.EventObserver
import io.fingerlabs.ex.app2app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel = MainViewModel()

    private lateinit var sharedUtil: SharedUtil

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedUtil = SharedUtil(applicationContext)

        with(mainViewModel) {
            app2AppRequestId.observe(this@MainActivity) {
                goToFavorletApp2App(it.peekContent())
            }
            connectedAddress.observe(this@MainActivity) {
                binding.textConnectedAddress.text = it
            }
            signatureHash.observe(this@MainActivity) {
                binding.textSignature.text = it
            }
            connectedAddress2.observe(this@MainActivity) {
                binding.textConnectedAddress2.text = it
            }
            signatureHash2.observe(this@MainActivity) {
                binding.textSignature2.text = it
            }
            resultSendCoin.observe(this@MainActivity, EventObserver{
                binding.textSendCoinResult.text = it
            })
            resultExecuteContractWithEncoded.observe(this@MainActivity, EventObserver{
                binding.textExecuteContractWithEncodedResult.text = it
            })
            progress.observe(this@MainActivity, EventObserver {
                binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            })
            errorToast.observe(this@MainActivity, EventObserver {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            })
            receivedChainId.observe(this@MainActivity) {
                binding.editChainId.setText("$it")
                sharedUtil.saveIntValue(Constant.NAME_CHAIN_ID, it)
            }
        }

        with(binding) {
            // 지갑연결
            val latestChainId = sharedUtil.loadIntValue(Constant.NAME_CHAIN_ID)
            if (latestChainId != -1) editChainId.setText("$latestChainId")

            // 메시지 서명
            val latestMessage = sharedUtil.loadStringValue(Constant.NAME_MESSAGE, "favorlet")
            editMessage.setText(latestMessage)

            // 지갑연결 & 메세지 서명
            val latestMessage2 = sharedUtil.loadStringValue(Constant.NAME_MESSAGE_2, "favorlet")
            editMessage2.setText(latestMessage2)

            // 코인 전송
            val latestToAddress = sharedUtil.loadStringValue(Constant.NAME_SEND_COIN_TO, "0x...")
            editTo.setText(latestToAddress)
            val latestCoinAmount = sharedUtil.loadStringValue(Constant.NAME_SEND_COIN_AMOUNT, "100000000000000000")
            editCoinAmount.setText(latestCoinAmount)

            // 컨트랙트 실행
            val latestContractAddress = sharedUtil.loadStringValue(Constant.NAME_EXECUTE_CONTRACT_ADDRESS, "")
            editContractAddress.setText(latestContractAddress)
            val latestData = sharedUtil.loadStringValue(Constant.NAME_EXECUTE_CONTRACT_DATA, "")
            editData.setText(latestData)
            val latestAmount = sharedUtil.loadStringValue(Constant.NAME_EXECUTE_CONTRACT_AMOUNT, "0")
            editAmount.setText(latestAmount)
            val latestGasLimit = sharedUtil.loadStringValue(Constant.NAME_EXECUTE_CONTRACT_GAS_LIMIT, "")
            editGasLimit.setText(latestGasLimit)

            btnConnectWallet.setOnClickListener {
                val chainId: Int = try {
                    editChainId.text.toString().toInt()
                } catch(error: Exception) {
                    viewModel.showErrorToast("ChainId 를 입력해 주세요!")
                    return@setOnClickListener
                }
                mainViewModel.requestConnectWallet(chainId)
            }
            btnSignMessage.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                val message = editMessage.text.toString()
                sharedUtil.saveStringValue(Constant.NAME_MESSAGE, message)
                mainViewModel.requestSignMessage(chainId, message)
            }
            btnConnectWalletSignMessage.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                val message = editMessage2.text.toString()
                sharedUtil.saveStringValue(Constant.NAME_MESSAGE_2, message)
                mainViewModel.requestConnectWalletSignMessage(chainId, message)
            }
            btnSendCoin.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                val toAddress = editTo.text.toString()
                val amount = editCoinAmount.text.toString()
                sharedUtil.saveStringValue(Constant.NAME_SEND_COIN_TO, toAddress)
                sharedUtil.saveStringValue(Constant.NAME_SEND_COIN_AMOUNT, amount)
                mainViewModel.requestSendCoin(chainId, toAddress, amount)
            }
            btnExecuteContractWithEncoded.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                val contractAddress = editContractAddress.text.toString()
                val data = editData.text.toString()
                val value = editAmount.text.toString()
                val gasLimit = editGasLimit.text.toString()
                sharedUtil.saveStringValue(Constant.NAME_EXECUTE_CONTRACT_ADDRESS, contractAddress)
                sharedUtil.saveStringValue(Constant.NAME_EXECUTE_CONTRACT_DATA, data)
                sharedUtil.saveStringValue(Constant.NAME_EXECUTE_CONTRACT_AMOUNT, value)
                sharedUtil.saveStringValue(Constant.NAME_EXECUTE_CONTRACT_GAS_LIMIT, gasLimit)
                mainViewModel.requestExecuteContractWithEncoded(chainId, contractAddress, data,  value, gasLimit)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        mainViewModel.receipt()
    }


    fun goToFavorletApp2App(requestId: String) {
        mainViewModel.execute(this, requestId)
    }
}