package io.fingerlabs.ex.app2app.common.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.fingerlabs.ex.app2app.Constant
import io.fingerlabs.ex.app2app.data.source.api.App2AppApi
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    private const val TIME_OUT = 10000L

    @Provides
    @Singleton
    fun provideApp2AppApi(@App2AppHttpClient httpClient: HttpClient) = App2AppApi(httpClient)


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class App2AppHttpClient


    @Provides
    @Singleton
    @App2AppHttpClient
    fun provideApp2AppHttpClient() = getHttpClientForAndroid {
        url { protocol = URLProtocol.HTTPS }
        host = Constant.BaseUrl.APP2APP
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
                                Log.i("FAVORLET", "$message")
                            } catch (e: Exception) {
                                Log.i("FAVORLET", "#####1 $message")
                            }
                        } else {
                            Log.i("FAVORLET", "#####2 $message")
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