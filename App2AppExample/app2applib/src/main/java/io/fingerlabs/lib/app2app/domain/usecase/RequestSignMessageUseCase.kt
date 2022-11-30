package io.fingerlabs.lib.app2app.domain.usecase

import io.fingerlabs.lib.app2app.data.repository.App2AppRepository
import io.fingerlabs.lib.app2app.data.source.remote.model.App2AppSignMessageRequest
import io.fingerlabs.lib.app2app.data.source.remote.model.App2AppSignMessageResponse

class RequestSignMessageUseCase constructor(
    private val app2AppRepository: App2AppRepository = App2AppRepository()
) {

    suspend operator fun invoke(
        request: App2AppSignMessageRequest
    ): App2AppSignMessageResponse = app2AppRepository.requestSignMessage(request)


}