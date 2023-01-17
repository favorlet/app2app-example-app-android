package io.fingerlabs.ex.app2app

import android.os.Bundle
import android.util.Log
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
            resultSendCoin.observe(this@MainActivity, EventObserver{
                binding.textSendCoinResult.text = it
            })
            resultExecuteContract.observe(this@MainActivity, EventObserver{
                binding.textExecuteContractResult.text = it
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

            // 코인 전송
            val latestToAddress = sharedUtil.loadStringValue(Constant.NAME_SEND_COIN_TO, "0x...")
            editTo.setText(latestToAddress)
            val latestCoinAmount = sharedUtil.loadStringValue(Constant.NAME_SEND_COIN_AMOUNT, "100000000000000000")
            editCoinAmount.setText(latestCoinAmount)

            // 컨트랙트 실행
            val latestContractAddress = sharedUtil.loadStringValue(Constant.NAME_EXECUTE_CONTRACT_ADDRESS, "0x...")
            editContractAddress.setText(latestContractAddress)
            val latestAbi = sharedUtil.loadStringValue(
                Constant.NAME_EXECUTE_CONTRACT_ABI,
                "[{\n" +
                        "    \"constant\": false,\n" +
                        "    \"inputs\": [\n" +
                        "      {\n" +
                        "        \"name\": \"_to\",\n" +
                        "        \"type\": \"address\"\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"name\": \"_value\",\n" +
                        "        \"type\": \"uint256\"\n" +
                        "      }\n" +
                        "    ],\n" +
                        "    \"name\": \"transfer\",\n" +
                        "    \"outputs\": [\n" +
                        "      {\n" +
                        "        \"name\": \"success\",\n" +
                        "        \"type\": \"bool\"\n" +
                        "      }\n" +
                        "    ],\n" +
                        "    \"payable\": false,\n" +
                        "    \"type\": \"function\"\n" +
                        "  }]"
            )
            editABI.setText(latestAbi)
            val latestParams = sharedUtil.loadStringValue(Constant.NAME_EXECUTE_CONTRACT_PARAMS, "[\"0x...\",100000000000000000]")
            editParams.setText(latestParams)
            val latestFunctionName = sharedUtil.loadStringValue(Constant.NAME_EXECUTE_CONTRACT_FUNCTION, "transfer")
            editFunctionName.setText(latestFunctionName)
            val latestAmount = sharedUtil.loadStringValue(Constant.NAME_EXECUTE_CONTRACT_AMOUNT, "0")
            editAmount.setText(latestAmount)
            val latestGasLimit = sharedUtil.loadStringValue(Constant.NAME_EXECUTE_CONTRACT_GAS_LIMIT, "")
            editGasLimit.setText(latestGasLimit)

            btnConnectWallet.setOnClickListener {
                val chainId: Int? = try {
                    editChainId.text.toString().toInt()
                } catch(error: Exception) {
                    null
                }
                mainViewModel.requestConnectWallet(chainId)
            }
            btnSignMessage.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                val message = editMessage.text.toString()
                sharedUtil.saveStringValue(Constant.NAME_MESSAGE, message)
                mainViewModel.requestSignMessage(chainId, message)
            }
            btnSendCoin.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                val toAddress = editTo.text.toString()
                val amount = editCoinAmount.text.toString()
                sharedUtil.saveStringValue(Constant.NAME_SEND_COIN_TO, toAddress)
                sharedUtil.saveStringValue(Constant.NAME_SEND_COIN_AMOUNT, amount)
                mainViewModel.requestSendCoin(chainId, toAddress, amount)
            }
            btnExecuteContract.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                val contractAddress = editContractAddress.text.toString()
                val abi = editABI.text.toString()
                val params = editParams.text.toString()
                val value = editAmount.text.toString()
                val functionName = editFunctionName.text.toString()
                val gasLimit = editGasLimit.text.toString()
                sharedUtil.saveStringValue(Constant.NAME_EXECUTE_CONTRACT_ADDRESS, contractAddress)
                sharedUtil.saveStringValue(Constant.NAME_EXECUTE_CONTRACT_ABI, abi)
                sharedUtil.saveStringValue(Constant.NAME_EXECUTE_CONTRACT_PARAMS, params)
                sharedUtil.saveStringValue(Constant.NAME_EXECUTE_CONTRACT_AMOUNT, value)
                sharedUtil.saveStringValue(Constant.NAME_EXECUTE_CONTRACT_FUNCTION, functionName)
                sharedUtil.saveStringValue(Constant.NAME_EXECUTE_CONTRACT_GAS_LIMIT, gasLimit)
                mainViewModel.requestExecuteContract(chainId, contractAddress, abi, params, value, functionName, gasLimit)
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