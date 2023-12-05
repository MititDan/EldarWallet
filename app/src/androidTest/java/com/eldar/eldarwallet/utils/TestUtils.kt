package com.eldar.eldarwallet.utils

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import okio.source
import java.util.concurrent.TimeUnit

fun<T> MockWebServer.configureServer(code: Int, delay: Long, filePath: String, `class`: Class<T>) {
    enqueue(
        MockResponse()
            .setResponseCode(
                code
            )
            .setBodyDelay(delay, TimeUnit.SECONDS)
            .setBody(
                readResourceFile(
                    `class`, filePath
                )
            )
    )
}

fun<T> readResourceFile(`class`: Class<T>, filePath: String): Buffer {
    val buffer = Buffer()
    `class`.classLoader?.getResourceAsStream(filePath)?.use { inputStream ->
        buffer.writeAll(inputStream.source())
    }
    return buffer
}