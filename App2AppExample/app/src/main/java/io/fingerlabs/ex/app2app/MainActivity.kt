package io.fingerlabs.ex.app2app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import io.fingerlabs.ex.app2app.common.eventwrapper.EventObserver
import io.fingerlabs.ex.app2app.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(mainViewModel) {
            app2AppRequestId.observe(this@MainActivity) {
                goToFavorletApp2App(it.peekContent())
            }
            connectedAddress.observe(this@MainActivity) {
                binding.textConnectedAddress.text = "연결된 지갑주소\n$it"
                binding.editParams.setText("[\"$it\",\"0x1707Cc19778A773c45C1EA03f62482481d3c0fBD\",\"10\"]")
            }
            signatureHash.observe(this@MainActivity) {
                binding.textSignature.text = "서명된 해시값\n$it"
            }
            resultSendCoin.observe(this@MainActivity, EventObserver{
                binding.textSendCoinResult.text = "전송결과 : $it"
            })
            resultExecuteContract.observe(this@MainActivity, EventObserver{
                binding.textExecuteContractResult.text = "실행결과 : $it"
            })

            progress.observe(this@MainActivity, EventObserver {
                binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            })
        }

        with(binding) {
            btnConnectWallet.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                mainViewModel.requestConnectWallet(chainId)
            }
            btnSignMessage.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                val message = editMessage.text.toString()
                mainViewModel.requestSignMessage(chainId, message)
            }
            btnSendCoin.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                val toAddress = editTo.text.toString()
                val amount = editCoinAmount.text.toString()
                mainViewModel.requestSendCoin(chainId, toAddress, amount)
            }
            editABI.setText("[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"safeTransferFrom\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{}]")
            btnExecuteContract.setOnClickListener {
                val chainId = editChainId.text.toString().toInt()
                val contractAddress = editContractAddress.text.toString()
                val abi = editABI.text.toString()
                val params = editParams.text.toString()
                val value = editAmount.text.toString()
                val functionName = editFunctionName.text.toString()
                mainViewModel.requestExecuteContract(chainId, contractAddress, abi, params, value, functionName)
            }

        }
    }


    override fun onResume() {
        super.onResume()
        mainViewModel.requestReceipt()
    }


    fun goToFavorletApp2App(requestId: String) {
        // FAVORLET 앱에 Applink 복귀기능이 없어서 딥링크 방식 사용.
        val uri = Uri.parse("https://favorlet.page.link/?link=https://favorlet.io?requestId=$requestId&apn=io.fingerlabs.wallet&isi=6443620205&ibi=io.fingerlabs.wl.favorlet&efr=1")

        // TODO: 추후 FAVORLET 앱에 Applink 복귀기능이 추가되면 아래 방식 사용.
//        val uri = Uri.parse("favorlet://app.link?link=https://favorlet.io?requestId=$requestId")

        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }



}