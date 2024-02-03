package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent

abstract class SyncAppender<E : ILogEvent> : BaseAppender<E>() {

    abstract fun writeOut(bytes: ByteArray)
    override fun doAppend(event: E) {
        writeOut(encoder.encode(event).toByteArray())
    }
}
