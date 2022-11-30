package io.fingerlabs.lib.app2app.domain.usecase

import android.app.Activity
import android.content.Intent
import android.net.Uri

class ExecuteUseCase {

    operator fun invoke(activity: Activity, requestId: String) {
        val uri = Uri.parse("https://favorlet.page.link/?link=https://favorlet.io?requestId=$requestId")
        activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}