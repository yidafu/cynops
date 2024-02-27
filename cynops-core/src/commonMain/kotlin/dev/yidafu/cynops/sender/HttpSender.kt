package dev.yidafu.cynops.sender

import dev.yidafu.cynops.helpers.httpClient
import dev.yidafu.cynops.helpers.runOnLog
import io.ktor.client.plugins.timeout
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode

val senderClient = httpClient {}

class HttpSender(private var endpoint: String) : Sender {
    override fun send(data: ByteArray): Boolean {
        runOnLog {
            val resp =
                senderClient.post(endpoint) {
                    timeout {
                        requestTimeoutMillis = 3000
                    }
                    headers {
                        append("Content-length", data.size.toString())
                        append("Content-Type", "application/json")
                    }
                }
            if (resp.status == HttpStatusCode.OK) {
                true
            } else {
                false
            }
        }

        return false
    }
}
