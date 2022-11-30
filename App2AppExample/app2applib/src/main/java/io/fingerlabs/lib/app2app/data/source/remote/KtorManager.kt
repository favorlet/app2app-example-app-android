package io.fingerlabs.lib.app2app.data.source.remote

import android.util.Log
import io.fingerlabs.lib.app2app.common.Constant
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

object KtorManager {
    private const val TAG = "app2app_lib"
    private const val TIME_OUT = 10_000L


    fun newInstance() = getHttpClientForAndroid {
        url { protocol = URLProtocol.HTTPS }
        host = Constant.BASEURL_APP2APP
        expectSuccess = false
    }


    private fun getHttpClientForAndroid(block: (HttpRequestBuilder.() -> Unit)? = null) =
        HttpClient(Android) {
            install(JsonFeature) {
                acceptContentTypes = acceptContentTypes + ContentType.Any

                this.serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                })
            }
            install(HttpTimeout) {
                connectTimeoutMillis = TIME_OUT
                requestTimeoutMillis = TIME_OUT
                socketTimeoutMillis = TIME_OUT
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        if (isJsonFormat(message)) {
                            try {
                                Log.i(TAG, "$message")
                            } catch (e: Exception) {
                                Log.i(TAG, "#####1 $message")
                            }
                        } else {
                            Log.i(TAG, "#####2 $message")
                        }
                    }
                }
                level = LogLevel.ALL
            }

            defaultRequest {
                // 요청 헤더값
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                if (block != null) block()
            }
        }


    private fun isJsonFormat(text: String) = text.startsWith("{") && text.endsWith("}")

}