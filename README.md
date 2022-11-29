# 개요

FAVORLET은 NFT의 활용성을 극대화시키는 NFT 전용 지갑입니다. NFT에 특화된 다양한 기능을 제공하고 블록체인 위의 NFT를 오프라인 세상과 연결하여 새로운 NFT 경험을 만들어나가고 있습니다. 만약 네이티브 앱이 블록체인과 상호작용을 하고자 한다면, FAVORLET의 App to App 연동(이하 app2app)을 통해 쉽게 구현하실 수 있습니다.

## 기능

- 지갑연결 (connectWallet)
- 메시지 서명 (signMessage)
- 코인 전송 (sendCoin)
- 컨트랙트 실행 (executeContract)

FAVORLET의 app2app은 위 4가지의 기능을 제공합니다. <b>지갑연결</b>은 네이티브 앱에서 사용자의 지갑 주소를 가져오기 위한 기능으로, 지갑 주소가 있으면 블록체인 상의 존재하는 지갑과 관련된 데이터를 조회할 수 있습니다. <b>메시지 서명</b>은 네이티브 앱에서 지정한 메시지를 서명하여, 지갑의 소유권 확인이나, 인증/승인의 역할을 할 수 있는 기능입니다. <b>코인 전송</b>은 체인의 플랫폼 코인을 전송하는 기능입니다. (현재는 클레이튼 메인넷만 지원) <b>트랜잭션 실행</b>은 컨트랙트의 함수를 실행하는 기능으로, 지정하는 함수명과 매개변수에 따라 다양한 기능을 수행할 수 있습니다.


## 동작흐름

- 요청 (Request)
- 실행 (Execute)
- 결과 (Receipt)

FAVORLET의 app2app은 <b>요청-실행-결과</b>의 3단계로 이루어집니다. <b>요청단계</b>는 네이티브 앱에서 app2app API를 사용하여 FAVORLET을 통해 하고자 하는 액션을 정의하는 단계입니다. 위에서 설명된 4가지의 기능 중 하나를 액션으로 지정할 수 있습니다. <b>실행단계</b>는 앞에서 지정한 액션을 실행하는 단계입니다. FAVORLET에 요청ID를 전달하면, FAVORLET이 해당 액션을 실행합니다. <b>결과단</b>는 FAVORLET에서 실행한 액션의 결과를 app2app API를 이용해 가져오는 단계입니다. 정리해보면 아래와 같습니다.

1. 네이티브 앱에서 수행할 기능을 지정해서 app2app API (POST /request)로 호출. Response로 요청ID가 전달됨.
2. 이전 과정에서 받아온 요청ID를 딥링크 방식으로 FAVORLET으로 전달.
3. FAVORLET에서는 요청ID에 해당하는 기능을 수행한 후, 결과를 app2app API로 전달. 네이티브 앱으로 복귀.
4. 네이티브 앱으로 복귀하는 시점에 app2app API (GET /receipt)로 실행결과를 가져옴.


# 샘플앱 둘러보기

샘플앱을 실행하시려면, <b></b>를 다운로드 받으신 후에, Android Studio에서 /App2AppExample 폴더를 열어주세요.
UI는 activity_main.xml에 구성되어 있고, 주요 로직은 ContentViewModel에 구현되어 있습니다. app2app API 연동에 사용되는 데이터 모델은 data/source/model 내에 있으며, API 호출과 관련된 로직은 data/source/api/App2AppApi에서 확인하실 수 있습니다.

# 시작하기

## 공통사항

#### Action
- <b>connectWallet</b> : 지갑 연결.    
- <b>signMessage</b> : 메시지 서명.    
- <b>sendCoin</b> : 해당 체인의 플랫폼 코인 전송.    
- <b>executeContract</b> : 컨트랙트 함수 실행.    
    
#### Status
- <b>requested</b> : app2app으로 수행할 액션 데이터를 요청한 상태.    
- <b>executed</b> : 요청한 액션을 FAVORLET이 수행한 상태.    
- <b>reverted</b> : 요청한 액션을 FAVORLET이 수행하지 못하는 상태.    
- <b>failed</b> : 요청한 액션을 FAVORLET이 수행했으나, 블록체인에서 실패한 상태.    
- <b>canceled</b> : 요청한 액션을 FAVORLET이 취소한 상태.    
- <b>succeed</b> : 요청한 액션을 FAVORLET이 수행하고, 블록체인에서도 성공한 상태.

## app2app API

app2app을 위해서는 네이티브 앱과 FAVORLET은 app2app API를 사용해서 액션을 지정하고, 실행한 후 결과를 공유해야 합니다. 이러한 일련의 단계는 위에서 언급한 바와 같이 3단계로 이루어지며, 각 단계는 아래와 같이 수행합니다.

### 요청 단계 (Request)

네이티브 앱에서 수행하고자 하는 기능을 Post /request AP를 이용해 액션으로 지정하는 단계입니다.

> ### POST /request

#### Parameter

예시
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

##### 컨트랙트 실행

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

#### Response

예시
```json
{
    "requestId": "31a7b2a4-cac1-4e94-b4b0-2c527bf00030",  // 요청 ID.
    "expiredAt": 1669624080 // 요청 만료시간.
}
```

### 실행 단계 (Execute)
  
Request 단계에서 얻은 Request ID를 URL Scheme 방식의 딥링크를 이용해 FAVORLET으로 전달합니다. FAVORLET은 전달받은 Request ID에 해당하는 액션을 수행합니다.
  
```kotlin
  val uri = Uri.parse("https://favorlet.page.link/?link=https://favorlet.io?requestId=${Request_ID}"
  startActivity(Intent(Intent.ACTION_VIEW, uri))
```
  
### 결과 단계 (Receipt)

FAVORLET에서 실행한 액션의 실행 결과를 app2app API를 이용해 가져오는 단계입니다.

> ### GET /receipt

#### Parameter

- <b>requestId (String)</b> : 요청 ID. 액션을 식별할 수 있는 값이며 FAVORLET에 전달한 값.

#### Response

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
    
    
# 제약사항
22.11.25.금 기준

#### 체인
- 현재는 클레이튼 메인넷만 지원.
- 추후 타 체인 지원 예정.

#### app2app 트랜잭션
- 설계는 복수 트랜잭션 처리가 고려되어 있으나, 현재는 1개의 트랜잭션만 처리.
- 만약 복수 개의 트랜잭션을 요청 시, FAVORLET에서는 첫번째 트랜잭션만 처리.

# 예정사항
네이티브 앱을 위한 app2app SDK 공개 예정.
