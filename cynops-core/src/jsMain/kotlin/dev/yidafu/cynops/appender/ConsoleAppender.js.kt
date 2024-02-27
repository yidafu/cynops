package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.codec.ICodec
import dev.yidafu.cynops.codec.PlainCodec

actual class ConsoleAppender actual constructor() : SyncAppender() {
    actual override var name: String = "Console"

    override var encoder: ICodec<ILogEvent> = PlainCodec

    override fun doAppend(event: ILogEvent) {
        console.log(encoder.encode(event))
    }
}
