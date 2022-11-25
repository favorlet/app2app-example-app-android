# app2app-example-app-android

# 개요    
     
  FAVORLET은 NFT의 활용성을 극대화시키는 NFT 전용 지갑입니다. NFT에 특화된 다양한 기능을 제공하고 블록체인 위의 NFT를 오프라인 세상과 연결하여 새로운 NFT 경험을 만들어나가고 있습니다. 만약 네이티브 앱이 지갑을 이용해 블록체인과 상호작용을 해야 한다면, FAVORLET의 App to App 연동(이하 app2app)을 통해 쉽게 구현하실 수 있습니다. 현재(22.11.25.기준)는 클레이튼 체인만 지원하고 있으나, 향후 다른 체인들을 순차적으로 지원할 예정입니다.
    
## 동작흐름    
    
> 요청 (Request)    
> 실행 (Execute)    
> 결과 (Receipt)    

  FAVORLET app2app은 Request, Execute, Receipt의 3단계로 이루어지며, app2app API와 FAVORLET을 이용합니다. <b>Request</b>단계는 네이티브 앱에서 app2app API를 사용하여 지갑에서 하고자 하는 액션을 정의하여 요청하는 단계입니다. 지갑연결, 메시지서명, 코인전송, 컨트랙트실행의 4가지 중 하나의 액션을 지정하여 요청할 수 있습니다. <b>Execute</b>단계는 Request단계에서 요청한 액션을 실행하는 단계입니다. FAVORLET에 Request ID를 전달하면, FAVORLET이 해당 액션을 실행합니다. <b>Receipt</b>단계는 FAVORLET에서 실행한 액션에 대한 결과를 가져오는 단계입니다. app2app API를 이용해 결과를 가져올 수 있습니다.
    
## 주요기능
    
> 지갑연결 (connectWallet)    
> 메시지 서명 (signMessage)    
> 코인 전송 (sendCoin)    
> 트랜잭션 실행 (executeContract)    

  FAVORLET app2app은 위 4가지의 기능을 제공하고 있으며, 이를 통해 블록체인과 상호작용 할 수 있습니다. <b>지갑연결</b>은 네이티브 앱에서 사용자의 지갑 주소를 가져오기 위한 기능으로, 지갑 주소가 있으면 블록체인 상에 존재하는 지갑과 연관된 데이터를 조회할 수 있습니다. <b>메시지 서명</b>은 네이티브 앱에서 지정한 메시지를 소유권을 가지고 있는(= 개인키를 가진) 지갑으로 서명하여, 소유권 확인이나 승인/인증을 할 수 있는 기능입니다. <b>코인 전송</b>은 체인의 플랫폼 코인을 전송하는 기능입니다. 선택된 체인에 따라 코인 종류 및 수수료가 다릅니다. <b>트랜잭션 실행</b>은 특정 컨트랙트의 함수를 실행하는 기능으로, 지정된 함수명과 매개변수에 따라 다양한 기능을 실행할 수 있습니다.
        
# 시작하기
    
## app2app API
    
### 공통 매개변수

#### Action
> <b>connectWallet</b> : 지갑 연결.    
> <b>signMessage</b> : 메시지 서명.    
> <b>sendCoin</b> : 해당 체인의 플랫폼 코인 전송.    
> <b>executeContract</b> : 컨트랙트 함수 실행.    
    
#### Status
> <b>requested</b> : app2app으로 수행할 액션 데이터를 요청한 상태.    
> <b>executed</b> : 요청한 액션을 FAVORLET이 수행한 상태.    
> <b>reverted</b> : 요청한 액션을 FAVORLET이 수행하지 못하는 상태.    
> <b>failed</b> : 요청한 액션을 FAVORLET이 수행했으나, 블록체인에서 실패한 상태.    
> <b>canceled</b> : 요청한 액션을 FAVORLET이 취소한 상태.    
> <b>succeed</b> : 요청한 액션을 FAVORLET이 수행하고, 블록체인에서도 성공한 상태.    

    
### Request

네이티브 앱에서 FAVORLET에 요청할 액션을 정의하여 app2app API 서버로 요청합니다.
올바른 형식의 요청일 경우, app2app API 서버는 Request ID를 응답합니다.

#### <b>POST /request</b>
    
#### 요청 예시    
    
##### 지갑연결    
```json
{
  "chainId": 8217,            // 체인 ID. 현재는 클레이튼(8217)만 지원.
  "action": "connectWallet",  // 액션.
  "blockChainApp": {          // 네이티브 앱 정보.
    "name": "app2app Sample", // 앱 이름.
    "successAppLink": "",     // <b>현재 지원안함.</b>
    "failAppLink": "",        // <b>현재 지원안함.</b>
  }
}
```

##### 메시지 서명    
```json
{
  "chainId": 8217,
  "action": "signMessage",
  "blockChainApp": {
    "name": "app2app Sample",
    "successAppLink": "",
    "failAppLink": "",
  },
  "signMessage": {          // 메시지 서명에 필요한 데이터.
    "from": "0x123...456",  // 서명할 지갑주소.
    "value": "message"      // 메시지 원문.
  }
}
```

##### 코인 전송
```json
{
  "chainId": 8217,
  "action": "signMessage",
  "blockChainApp": {
    "name": "app2app Sample",
    "successAppLink": "",
    "failAppLink": "",
  },
  "transactions": [           // 실행할 트랜잭션 정보. 현재 FAVORLET은 1개의 트랜잭션만 처리.
    {
      "from": "0x123...456",  // 보내는 지갑주소.
      "to": "0x654...321",    // 받는 지갑주소.
      "value": "100"          // 보내는 코인 수량. (단위: peb)
    }
  ]
}
```

##### 컨트랙트  실행

```json
{
  "chainId": 8217,
  "action": "executeContract",
  "blockChainApp": {
    "name": "app2app Sample",
    "successAppLink": "",
    "failAppLink": "",
  },
  "transactions": [           // 실행할 트랜잭션 정보. 현재 FAVORLET은 1개의 트랜잭션만 처리.
    {
      "from": "0x123...456",  // 트랜잭션을 전송할 지갑주소.
      "to": "0x654...321",    // 컨트랙트 주소.
      "value": "0",           // 보내는 코인 수량. 단, non-payable 함수인 경우에는 0으로 지정해야 함.
      "abi": "{\"inputs\":[{\"internalType\":\"address\",\"name\":\"src\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"dst\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"wad\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\",\"signature\":\"0x23b872dd\"}", // 실행할 함수의 ABI 정보.
      "params": "[\"0x123...456\", \"0x654...321\", 122]",   // 실행할 함수의 매개변수. JSONArray 문자열이어야 함.
      "functionName": "transferFrom"  // 실행할 함수명.
    }
  ]
}
```
    
    
### Execute
  
Request 단계에서 얻은 Request ID를 URL Scheme 방식의 딥링크를 이용해 FAVORLET으로 전달합니다. FAVORLET은 전달받은 Request ID에 해당하는 액션을 수행합니다.
  
```kotlin
  val uri = Uri.parse("https://favorlet.page.link/?link=https://favorlet.io?requestId=${Request_ID}"
  startActivity(Intent(Intent.ACTION_VIEW, uri))
```
  
### Receipt

FAVORLET과의 연동을 마치고 네이티브 앱으로 돌아오면, 네이티브 앱은 app2app API (GET /receipt)를 사용해서 결과를 가져와야 합니다.
    
#### GET /receipt?requestId=${Request_ID}
  
##### Parameter
requestId (String): 요청했던 액션의 Request ID.
    
##### Response

예시
  
지갑연동 (connectWallet)
    
```json
  {
    "requestId": "96a1f659-3cc4-42db-aa87-7ade549df66d",  // 요청 ID.
    "expiredAt": 1664340943,      // 요청 만료시간.
    "action" : "connectWallet",   // 액션.
    "connectWallet": {            // 연결된 지갑 정보.
        "status": "succeed",      // 연동상태.
        "address": "0x123...123"  // 연결된 지갑 주소.
    }
}
```
  
메시지 서명 (signMessage)
```json
  {
    "requestId": "879855c2-fd2e-4ac9-bc11-2939b7ca9697",
    "expiredAt": 1664341330,
    "action": "signMessage",
    "signMessage": {            // 메시지 서명 정보.
        "status": "succeed",    // 연동상태.
        "signature": "0xasdkasldjwqevnwrejkqwkeqlwkejq" // 해시값.
    }
}
```
  
코인 전송 (sendCoin)
```json
  {
    "requestId": "19a58b08-4c0d-4552-8174-c9a767668f43",
    "expiredAt": 1664341165,
    "action" : "sendCoin",
    "transactions": [           // 코인전송 결과 정보.
        {
            "status": "succeed",      // 연동상태.
            "txHash": "0x123...123"   // 트랜잭션 해시.
        }
    ]
}
```

컨트랙트 함수 실행 (executeContract)
```json
  {
    "requestId": "278183ab-d3cb-4563-b0d4-ece1a2559f03",
    "expiredAt": 1664341448,
    "action": "executeContract",
    "transactions": [               // 컨트랙트 함수 실행 결과 정보.
        {
            "status": "succeed",    // 연동상태.
            "txHash": "0x123...123" // 트랜잭션 해시.
        }
    ]
}
```
    
    
## 제약사항
22.11.25.금 기준
    
### 체인
* 현재는 클레이튼만 지원.
* 추후 타 체인 지원 예정.

### 네이티브앱 - FAVORLET 연동
* 현재는 URL Scheme, Intent Scheme 방식의 딥링크만 지원.
* 추후 AppLink 방식 지원 예정.

### app2app - 코인전송(sendCoin), 트랜잭션 실행(executeContract)
* 현재는 1개의 트랜잭션만 처리. 복수개의 트랜잭션 요청시 첫번째 트랜잭션만 처리.
