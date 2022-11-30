package io.fingerlabs.lib.app2app.common

enum class App2AppStatus(val value: String) {
    REQUESTED(value = "requested"),
    EXECUTED(value = "executed"),
    REVERTED(value = "reverted"),
    FAILED(value = "failed"),
    CANCELED(value = "canceled"),
    SUCCEED(value = "succeed"),
}