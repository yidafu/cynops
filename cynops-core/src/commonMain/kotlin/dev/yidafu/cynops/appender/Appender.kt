package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.LoggerContext
import dev.yidafu.cynops.listener.EventListener

interface Appender : EventListener {
    abstract var name: String
    abstract var context: LoggerContext

    fun doAppend(event: ILogEvent)

    fun flush()
}
