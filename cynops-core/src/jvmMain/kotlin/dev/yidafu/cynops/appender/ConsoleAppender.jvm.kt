package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.codec.ICodec
import dev.yidafu.cynops.codec.StringCodec

actual class ConsoleAppender actual constructor() : SyncAppender() {
    actual override var name: String = "Console"

    override var encoder: ICodec<ILogEvent> = StringCodec

    override fun doAppend(event: ILogEvent) {
        println(encoder.encode(event))
    }
}
