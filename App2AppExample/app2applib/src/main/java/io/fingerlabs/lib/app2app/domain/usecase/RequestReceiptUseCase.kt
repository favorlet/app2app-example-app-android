package io.fingerlabs.lib.app2app.domain.usecase

import io.fingerlabs.lib.app2app.data.repository.App2AppRepository
import io.fingerlabs.lib.app2app.data.source.remote.model.App2AppReceiptResponse

class RequestReceiptUseCase constructor(
    private val app2AppRepository: App2AppRepository = App2AppRepository()
) {

    suspend operator fun invoke(
        requestId: String
    ): App2AppReceiptResponse = app2AppRepository.requestReceipt(requestId)

}