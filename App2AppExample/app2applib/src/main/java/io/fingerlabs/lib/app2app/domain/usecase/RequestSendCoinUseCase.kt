package io.fingerlabs.lib.app2app.domain.usecase

import io.fingerlabs.lib.app2app.data.repository.App2AppRepository
import io.fingerlabs.lib.app2app.data.source.remote.model.App2AppSendCoinRequest
import io.fingerlabs.lib.app2app.data.source.remote.model.App2AppSendCoinResponse

class RequestSendCoinUseCase constructor(
    private val app2AppRepository: App2AppRepository = App2AppRepository()
) {

    suspend operator fun invoke(
        request: App2AppSendCoinRequest
    ): App2AppSendCoinResponse = app2AppRepository.requestSendCoin(request)

}