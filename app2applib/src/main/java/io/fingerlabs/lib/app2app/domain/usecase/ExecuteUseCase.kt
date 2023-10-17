package io.fingerlabs.lib.app2app.domain.usecase

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri

internal class ExecuteUseCase {

    operator fun invoke(context: Context, requestId: String) {
        val uri = Uri.parse("https://favorlet.page.link/?link=https://favorlet.io?requestId=$requestId&apn=io.fingerlabs.wallet&efr=1")
        context.startActivity(
            Intent(Intent.ACTION_VIEW, uri)
                .addFlags(FLAG_ACTIVITY_NEW_TASK)
        )
    }
}