package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.codec.ICodec
import dev.yidafu.cynops.codec.LogCodec
import dev.yidafu.cynops.config.CynopsConfig
import dev.yidafu.cynops.sender.HttpSender
import dev.yidafu.cynops.sender.LokiSteams
import dev.yidafu.cynops.sender.Sender
import dev.yidafu.cynops.sender.form
import io.ktor.utils.io.core.*

data class CynopsConfigHttpAppender(
    val endpoint: String,
)

const val HttpAppenderConfigKey = "httpAppender"

internal val CynopsConfig.mHttpAppender: CynopsConfigHttpAppender
    get() =
        getOrInit(HttpAppenderConfigKey) {
            CynopsConfigHttpAppender("http://127.0.0.1:3100/loki/api/v1/push")
        }

fun CynopsConfig.http() {
    println("http config ${hashCode()}")

    appenderList.add(HttpAppender(this))
}

class HttpAppender(
    config: CynopsConfig,
    override var name: String = "Http",
    override var encoder: ICodec<ILogEvent> = LogCodec,
) : BufferedAppender(config) {
    private val sender: Sender = HttpSender(config.mHttpAppender.endpoint)

    override suspend fun asyncAppend(eventArray: List<ILogEvent>) {
        val log = LokiSteams.form(eventArray)
        println("streams $log")
        sender.send(log.toString().toByteArray())
    }
}
